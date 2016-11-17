package org.ao.suite.test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestDriver {
	
	
	private String name;
	private LinkedHashMap<String, Object> arguments;
	private TestModel testModel;
	
	private static Logger TestLogger = LoggerFactory.getLogger(TestDriver.class);
	
	public TestDriver(String name, LinkedHashMap<String, Object> arguments) 
			throws JsonParseException, JsonMappingException, IOException {
		
		this.name = name;
		this.arguments = arguments;
		
		loadTest();
		putArguments();
		storeVars();
		
	}
	
	public void Run() {
		
		TestLogger.debug("{} is running now.", name);
		
	}
	
	private void loadTest() 
			throws JsonParseException, JsonMappingException, IOException {
		
		testModel = new ObjectMapper().readValue(new File(this.name), TestModel.class);
		TestLogger.debug("test = {}", testModel);
		
	}
	
	private void putArguments() {
		
		arguments.forEach((k, v) -> {
			if (testModel.getArguments().containsKey(k))
				testModel.getArguments().replace(k, v);
		} );
		
		TestLogger.debug("updated test arguments {}", testModel);
	}

	private void storeVars() {
		
	}
}
