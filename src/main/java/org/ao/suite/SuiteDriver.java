package org.ao.suite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.LinkedHashMap;

import org.ao.suite.test.TestDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope("prototype")
public class SuiteDriver {
	
	public String SuiteId;
	
	@Autowired
	private SuiteProperty suiteProp;
	
	private static Logger SuiteLogger = LoggerFactory.getLogger(SuiteDriver.class);
	private SuiteModel suiteModel;
	private LinkedHashMap<String, TestDriver> testDrivers; 
	
	public void Load(String suitePathName) throws JsonParseException, IOException {
		String pathName = normalizePath(suiteProp.home, suitePathName);
		
		suiteModel = new ObjectMapper().readValue(new File(pathName), SuiteModel.class);
		
		loadTests();

	}

	private void loadTests() {
		testDrivers = new LinkedHashMap<String, TestDriver>();
		
		for (SuiteTestModel suiteTestModel : suiteModel.getTests()) {
			
			String pathName = normalizePath(suiteProp.testHome, suiteTestModel.getFileName());
			
			testDrivers.put(pathName, new TestDriver(pathName, suiteTestModel.getArguments()));
			
		}
		
		testDrivers.containsKey("");
			
	}
	
	private String normalizePath(String path, String name) {
		String normalizedPath;
		if (path.length() > 0) {
			if (path.endsWith("/"))
				normalizedPath = path.concat(name);
			else
				normalizedPath = path.concat("/").concat(name);
		}
		else
			normalizedPath = name;
		
		if (!normalizedPath.endsWith(".json"))
			normalizedPath = normalizedPath.concat(".json");
		
		return normalizedPath;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(suiteModel.toString());
		
		
		return sb.toString();
	}
}
