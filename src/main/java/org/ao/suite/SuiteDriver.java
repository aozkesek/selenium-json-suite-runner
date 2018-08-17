package org.ao.suite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.ao.suite.model.ObjectModel;
import org.ao.suite.model.SuiteModel;
import org.ao.suite.model.SuiteTestModel;
import org.ao.suite.test.TestDriver;
import org.ao.suite.test.command.exception.CommandNotFoundException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SuiteDriver {
	
	public String SuiteId;
	
	private RemoteWebDriver webDriver;
	
	private SuiteModel suite;
	private ObjectModel object;
	private List<TestDriver> tests; 
	
	@Autowired
	private SuiteProperty suiteProp;
	@Autowired
	private ObjectContainer objectContainer;
	@Autowired
	private ObjectMapperFactory objectMapperFactory;
	@Autowired
	private AnnotationConfigApplicationContext appCtx;
	
	private static Logger logger = LoggerFactory.getLogger(SuiteDriver.class);
	
	@PostConstruct
	public void init() throws MalformedURLException {
		
		if (suiteProp.remoteUrl != null && !suiteProp.remoteUrl.isEmpty()) {
			if (suiteProp.webDriver.equals("firefox"))
				webDriver = new RemoteWebDriver(
						new URL(suiteProp.remoteUrl), 
						new FirefoxOptions());
			else if (suiteProp.webDriver.equals("chrome"))
				webDriver = new RemoteWebDriver(
						new URL(suiteProp.remoteUrl),
						new ChromeOptions());
		}
		else {
			if (suiteProp.webDriver.equals("firefox"))
				webDriver = new FirefoxDriver();
			else if (suiteProp.webDriver.equals("chrome"))
				webDriver = new ChromeDriver();
		}
		
		webDriver.manage()
			.timeouts()
			.implicitlyWait(suiteProp.timeOut, TimeUnit.MILLISECONDS)
			.pageLoadTimeout(suiteProp.timeOut, TimeUnit.MILLISECONDS)
			.setScriptTimeout(suiteProp.timeOut, TimeUnit.MILLISECONDS);
		
	}
	
	@PreDestroy
	public void destroy() {
		webDriver.quit();
		logger.debug("WebDriver->quit called.");
	}
	
	public RemoteWebDriver getWebDriver() {
		return this.webDriver;
	}
	
	public ObjectContainer getObjectContainer() {
		return this.objectContainer;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public void RunTests() {
		
		try {
		        webDriver.get(suite.getTestUrl());
			tests.forEach((t) -> t.run());
		}
		catch(Exception e) {
			throw e;
		}
		finally {
		        webDriver.quit();
		}
		
	}
	
	// load suite
	public void loadSuite(String suitePathName) throws IOException, CommandNotFoundException {
		String pathName = fullPathName(suiteProp.home, suitePathName);
		
		SuiteId = UUID.randomUUID().toString().concat("_").concat(suitePathName);
		
		logger.debug("loading {}", suitePathName);
		suite = objectMapperFactory.getObjectMapper()
				.readValue(new File(pathName), SuiteModel.class);
		logger.debug("{} loaded.\n{}", suitePathName, suite);
		
		loadSuiteObjects();
		
		loadSuiteTests();

	}

	private void loadSuiteObjects() throws IOException {
		String pathName = fullPathName(suiteProp.objectsHome, suite.getObjectRepository());
		
		logger.debug("loading {}", pathName);
		object = objectMapperFactory.getObjectMapper()
				.readValue(new File(pathName), ObjectModel.class);
		logger.debug("{} loaded.\n{}", pathName, object);
		
		// then put them into container, so others can access them
		object.getObjects().
			forEach((k,v) -> objectContainer.putVariable(k, v));
		
	}
	
	public TestDriver loadTest(String name, Map<String, String> arguments) 
			throws CommandNotFoundException, IOException {
		
		String pathName = fullPathName(suiteProp.testsHome, suite.getTestPath());
		pathName = fullPathName(pathName, name);
		
		return appCtx.getBean(TestDriver.class).init(this, pathName, arguments);
	}
	
	private void loadSuiteTests() throws CommandNotFoundException, IOException {
		tests = new ArrayList<TestDriver>();
		
		for (SuiteTestModel test : suite.getTests()) 
			tests.add(loadTest(test.getFileName(), test.getArguments()));		
		
	}
	
	private PrintWriter reportWriter = null;
	
	public PrintWriter getReportWriter() {
	        return reportWriter;
	}
	
	public void openReportWriter() {
		String reportFileName = SuiteId.replace('/', '_').replace('\\', '_');
		File reportFile = new File(fullPathName(suiteProp.reportsHome, reportFileName));
    
		try {
			this.reportWriter = new PrintWriter(new BufferedWriter(new FileWriter(reportFile)));
		} catch (IOException e) {
			logger.error("{}", e);
		}
	}
	
	private boolean isNeededCommaTest = false;
	
	public boolean isNeededCommaTest() { return isNeededCommaTest; }
	public void setNeededCommaTest(boolean isNeeded) { isNeededCommaTest = isNeeded; }
	
	private boolean isNeededCommaCommand = false;
        
	public boolean isNeededCommaCommand() { return isNeededCommaCommand; }
        public void setNeededCommaCommand(boolean isNeeded) { isNeededCommaCommand = isNeeded; }
        
	private String fullPathName(String path, String name) {
		String normalizedPath;
		if (path.length() > 0) {
			if (path.endsWith("/"))
				normalizedPath = path.concat(name);
			else
				normalizedPath = path.concat("/").concat(name);
		} else
			normalizedPath = name;
		
		if (normalizedPath.endsWith("/"))
			return normalizedPath;
		
		if (!normalizedPath.endsWith(".".concat(suiteProp.format)))
			normalizedPath = normalizedPath.concat(".".concat(suiteProp.format));
		
		return normalizedPath;
	}
	
	
}
