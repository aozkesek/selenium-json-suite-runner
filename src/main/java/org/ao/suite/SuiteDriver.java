package org.ao.suite;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.ao.suite.model.ObjectModel;
import org.ao.suite.model.SuiteModel;
import org.ao.suite.model.SuiteTestModel;
import org.ao.suite.test.TestContainer;
import org.ao.suite.test.TestDriver;
import org.ao.suite.test.command.exception.CommandNotFoundException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	private TestContainer testContainer;
	@Autowired
	private ObjectContainer objectContainer;
	
	private static Logger logger = LoggerFactory.getLogger(SuiteDriver.class);
	
	@PostConstruct
	public void init() throws MalformedURLException {
		
		if (suiteProp.remoteUrl != null && suiteProp.remoteUrl.length() > 0) {
			if (suiteProp.webDriver.equals("firefox"))
				webDriver = new RemoteWebDriver(
						new URL(suiteProp.remoteUrl), 
						DesiredCapabilities.firefox());
			else if (suiteProp.webDriver.equals("chrome"))
				webDriver = new RemoteWebDriver(
						new URL(suiteProp.remoteUrl),
						DesiredCapabilities.chrome());
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
	
	public void RunTests() {
		
		webDriver.get(suite.getTestUrl());
		
		try {
			tests.forEach((t) -> t.run());
		}
		catch(Exception e) {
			logger.error("interrupted by ", e);
		}
		
		webDriver.quit();
	}
	
	public void Load(String suitePathName) throws JsonParseException, IOException, CommandNotFoundException {
		String pathName = getFullPathName(suiteProp.home, suitePathName);
		
		logger.debug("suit is loading {}", suitePathName);
		suite = new ObjectMapper().
						readValue(new File(pathName), SuiteModel.class);
		logger.debug("suite has loaded as {}", suite);
		
		loadObjects();
		
		loadTests();

	}

	public TestDriver loadTest(String testFileName, LinkedHashMap<String, Object> testArguments) 
			throws JsonParseException, JsonMappingException, CommandNotFoundException, IOException {
		
		String pathName = getFullPathName(suiteProp.testHome, suite.getTestPath());
		pathName = getFullPathName(pathName, testFileName);
		
		logger.debug("test is loading/getting from {}", pathName);
		
		if (!testContainer.containsTestDriverKey(pathName))
			testContainer.putTestDriver(pathName, 
					new TestDriver(this, pathName, testArguments));
		
		return testContainer.getTestDriver(pathName);
	}
	
	private void loadObjects() throws JsonParseException, JsonMappingException, IOException {
		String pathName = getFullPathName(suiteProp.objectsHome, suite.getObjectRepository());
		
		logger.debug("object is loading {}", pathName);
		object = new ObjectMapper().
						readValue(new File(pathName), ObjectModel.class);
		logger.debug("object has loaded as {}", object);
		
		object.getObjects().
			forEach((k,v) -> objectContainer.putVariable(k, v));
	}
	
	private void loadTests() throws JsonParseException, JsonMappingException, IOException, CommandNotFoundException {
		tests = new ArrayList<TestDriver>();
		
		for (SuiteTestModel suiteTestModel : suite.getTests()) 
			tests.add(loadTest(suiteTestModel.getFileName(), suiteTestModel.getArguments()));
			
	}
	
	public String getFullPathName(String path, String name) {
		String normalizedPath;
		if (path.length() > 0) {
			if (path.endsWith("/"))
				normalizedPath = path.concat(name);
			else
				normalizedPath = path.concat("/").concat(name);
		}
		else
			normalizedPath = name;
		
		if (!normalizedPath.endsWith(".json") && !normalizedPath.endsWith("/"))
			normalizedPath = normalizedPath.concat(".json");
		
		return normalizedPath;
	}
	
	
}
