package org.ao.suite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.ao.suite.test.TestDriver;
import org.ao.suite.test.command.CommandNotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
	
	private WebDriver webDriver;
	private SuiteModel suite;
	private ObjectModel object;
	private List<TestDriver> tests; 
	
	@Autowired
	private SuiteProperty suiteProp;
	@Autowired
	private TestContainer testContainer;
	@Autowired
	private ObjectContainer objectContainer;
	
	
	private static Logger SuiteLogger = LoggerFactory.getLogger(SuiteDriver.class);
	
	@PostConstruct
	public void init() {
		
		if (suiteProp.webDriver.equals("firefox"))
			webDriver = new FirefoxDriver();
		else if (suiteProp.webDriver.equals("chrome"))
			webDriver = new ChromeDriver();
		
	}
	
	@PreDestroy
	public void destroy() {
		webDriver.quit();
		SuiteLogger.debug("WebDriver->quit called.");
	}
	
	public WebDriver getWebDriver() {
		return this.webDriver;
	}
	
	public ObjectContainer getObjectContainer() {
		return this.objectContainer;
	}
	
	public void RunTests() {
		
		webDriver.get(suite.getTestUrl());
		
		try {
			tests.forEach((t) -> t.Run());
		}
		catch(Exception e) {
			SuiteLogger.error("interrupted by ", e);
		}
		
		webDriver.quit();
	}
	
	public void Load(String suitePathName) throws JsonParseException, IOException, CommandNotFoundException {
		String pathName = getFullPathName(suiteProp.home, suitePathName);
		
		SuiteLogger.debug("suit is loading {}", suitePathName);
		suite = new ObjectMapper().
						readValue(new File(pathName), SuiteModel.class);
		SuiteLogger.debug("suite has loaded as {}", suite);
		
		loadObjects();
		
		loadTests();

	}

	private void loadObjects() throws JsonParseException, JsonMappingException, IOException {
		String pathName = getFullPathName(suiteProp.objectsHome, suite.getObjectRepository());
		
		SuiteLogger.debug("object is loading {}", pathName);
		object = new ObjectMapper().
						readValue(new File(pathName), ObjectModel.class);
		SuiteLogger.debug("object has loaded as {}", object);
		
		object.getObjects().
			forEach((k,v) -> objectContainer.putVariable(k, v));
	}
	
	private void loadTests() throws JsonParseException, JsonMappingException, IOException, CommandNotFoundException {
		tests = new ArrayList<TestDriver>();
		
		for (SuiteTestModel suiteTestModel : suite.getTests()) {
			
			String pathName = getFullPathName(suiteProp.testHome, suite.getTestPath());
			pathName = getFullPathName(pathName, suiteTestModel.getFileName());
			
			SuiteLogger.debug("test is loading/getting from {}", pathName);
			
			if (!testContainer.containsTestDriverKey(pathName))
				testContainer.putTestDriver(pathName, 
						new TestDriver(this, pathName, suiteTestModel.getArguments()));
			
			tests.add(testContainer.getTestDriver(pathName));
			
		}
			
	}
	
	private String getFullPathName(String path, String name) {
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
