package org.ao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.ao.model.Model;
import org.ao.model.ObjectModel;
import org.ao.model.SuiteModel;
import org.ao.model.SuiteTestModel;
import org.ao.command.exception.CommandNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
/* in multi-thread environment, every suite has to use it's own driver */
@Scope("prototype")
public class SuiteDriver implements Callable<Boolean> {

    public enum Level {
        ERROR, WARN, INFO, DEBUG
    }

    private final String suiteId;
    private final String suiteToRun;

    private RemoteWebDriver webDriver;

    private SuiteModel suite;
    private List<TestDriver> tests;

    private static final Logger logger = LoggerFactory.getLogger(SuiteDriver.class);

    @Autowired
    private ApplicationContext context;
    @Autowired
    private SuiteProperties suiteProperties;
    @Autowired
    private ObjectContainer objectContainer;
    @Autowired
    private ObjectMapperFactory objectMapperFactory;
    @Autowired
    private ThreadPoolTaskExecutor executor;

    public SuiteDriver(String suiteToRun) {
        this.suiteToRun = suiteToRun;
        this.suiteId = UUID.randomUUID().toString().concat("_").concat(suiteToRun);
    }

    @PostConstruct
    private void prepareSuit() throws CommandNotFoundException, IOException {
        loadSuite();
        loadSuiteObjects();
        loadSuiteTests();
    }

    @Override
    public String toString() {
        return suiteId;
    }

    @Override
    public Boolean call() {

        try {

            initDriver();

            webDriver.get(suite.getTestUrl());

            runTests();

        } catch (CommandNotFoundException | IOException e) {
            logError("Suite is interrupted by; ", e);
            return false;
        } finally {
            if (null != webDriver) {
                webDriver.quit();
            }
        }

        return true;
    }

    private void runTests() {

        tests.forEach(t -> {
            Future<Boolean> ft = executor.submit(t);
            try {
                ft.get();
            } catch (InterruptedException | ExecutionException e) {
                logError("Test {} is ended by exception {}", t, e);
            }
        });

    }

    private boolean isBlankOrEmpty(String s) {
        return null == s || s.isBlank() || s.isEmpty();
    }

    private void initDriver() throws MalformedURLException {

        logInfo("A {} web-driver is being prepared for local or remote {}", suiteProperties.driver,
                suiteProperties.remoteUrl);

        if (isBlankOrEmpty(suiteProperties.remoteUrl)) {
            if (suiteProperties.driver.equals("firefox"))
                webDriver = new FirefoxDriver();
            else if (suiteProperties.driver.equals("chrome"))
                webDriver = new ChromeDriver();
        } else {
            if (suiteProperties.driver.equals("firefox"))
                webDriver = new RemoteWebDriver(new URL(suiteProperties.remoteUrl), new FirefoxOptions());
            else if (suiteProperties.driver.equals("chrome"))
                webDriver = new RemoteWebDriver(new URL(suiteProperties.remoteUrl), new ChromeOptions());
        }

        if (null == webDriver) {
            throw new IllegalArgumentException("web-driver");
        }

        webDriver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofMillis(suiteProperties.timeout))
                .pageLoadTimeout(Duration.ofMillis(suiteProperties.timeout))
                .scriptTimeout(Duration.ofMillis(suiteProperties.timeout));

    }

    public WebElement findElementBy(By by) {
        return webDriver.findElement(by);
    }

    public List<WebElement> findElementsBy(By by) {
        return webDriver.findElements(by);
    }

    public String getSuiteId() {
        return suiteId;
    }

    private void loadSuite() throws IOException, CommandNotFoundException {
        Path suitePath = Path.of(suiteProperties.path, suiteToRun);

        logDebug("Suite {} is being loaded...", suitePath);
        suite = objectMapperFactory.getObjectMapper().readValue(suitePath.toFile(), SuiteModel.class);

    }

    private void loadSuiteObjects() throws IOException {
        Path objectPath = Path.of(suiteProperties.objectsPath, suite.getObjectRepository());

        logDebug("Objects {} are being loaded...", objectPath);
        ObjectModel object = objectMapperFactory.getObjectMapper().readValue(objectPath.toFile(), ObjectModel.class);

        // then put them into container, so others can access them
        object.getObjects().forEach(objectContainer::putVariable);

    }

    public TestDriver loadTest(String name, Map<String, String> arguments)
            throws CommandNotFoundException, IOException {

        Path testPath = Path.of(suiteProperties.testsPath, suite.getTestPath(), name);
        return context.getBean(TestDriver.class, this, testPath, arguments);
    }

    private void loadSuiteTests() throws CommandNotFoundException, IOException {
        tests = new ArrayList<>();

        for (SuiteTestModel test : suite.getTests()) {
            tests.add(loadTest(test.getFileName(), test.getArguments()));
        }

    }

    public void logError(String format, Object... args) {
        log(Level.ERROR, format, args);
    }

    public void logInfo(String format, Object... args) {
        log(Level.INFO, format, args);
    }

    public void logWarn(String format, Object... args) {
        log(Level.WARN, format, args);
    }

    public void logDebug(String format, Object... args) {
        log(Level.DEBUG, format, args);
    }

    private void log(Level level, String format, Object... args) {
        format = "[" + suiteId + "] " + format;

        if (args == null)
            return;

        for (int i = 0; i < args.length; i++) {
            if (!(args[i] instanceof Model))
                continue;

            try {
                args[i] = objectMapperFactory
                        .getObjectMapper()
                        .writeValueAsString(args[i]);
            } catch (JsonProcessingException e) {
                logger.error("This {} could not translated by {}", args[i], e);
            }
        }

        switch (level) {
            case DEBUG -> logger.debug(format, args);
            case INFO -> logger.info(format, args);
            case WARN -> logger.warn(format, args);
            case ERROR -> logger.error(format, args);
        }
        
    }
	
	private PrintWriter reportWriter = null;

	public void openReport() {
		String reportFileName = suiteId.replace('/', '_').replace('\\', '_');
        File reportFile = Path
            .of(suiteProperties.reportsPath, reportFileName)
            .toFile();
    
		try {
			this.reportWriter = new PrintWriter(
                new BufferedWriter(
                    new FileWriter(reportFile)
                    )
                );
		} catch (IOException e) {
			logError("{}", e);
		}
    }
    
    public void closeReport() {
        reportWriter.close();
    }
	
	private boolean isTestCommaNeeded = false;
	private boolean isCommandCommaNeeded = false;
    
    public void putVariable(String key, String value) {
        objectContainer.putVariable(key, value);
    }

    public String getReplacedVariable(String var) {
        return objectContainer.getReplacedVariable(var);
    }

	public WebDriver getWebDriver() {
		return null;
	}

    public void report(String format, Object... args) {
        
        if (null == reportWriter) {
            openReport();
        }
        reportWriter.printf(format, args);
    }

    public void reportTestStart(String test) {
        
        if (isTestCommaNeeded) {
            report(", ");
        }
        
        report("{ \"name\": \"%1$s\", \"commands\": [\n", test);
        
        isTestCommaNeeded = true;
        isCommandCommaNeeded = false;    
    }

    public void reportCommandStart() {

        if (isCommandCommaNeeded) {
            report(", { \"test\": ");
        } 
        else {
            report("{ \"test\": ");
        }

        isTestCommaNeeded = false;
        isCommandCommaNeeded = false;

    }
    
    public void reportCommandEnd(String command, boolean isSuccessful) {

        if (command.equals("runTest")) {
            report(", ");
        } 
        else {
            if (isCommandCommaNeeded) {
                report(", { ");
            }
            else {
                report("{ ");
            }
        }
            
        report("\"command\": \"%1$s\", \"isSuccessful\": %2$b }\n", 
            command, isSuccessful);

        isCommandCommaNeeded = true;

    }
}
