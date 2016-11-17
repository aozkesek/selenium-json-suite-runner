package org.ao.suite.test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestDriver {
	
	
	private String name;
	private LinkedHashMap<String, Object> arguments;
	
	public TestDriver(String name, LinkedHashMap<String, Object> arguments) 
			throws JsonParseException, JsonMappingException, IOException {
		
		this.name = name;
		this.arguments = arguments;
		
		load();
	}
	
	private void load() 
			throws JsonParseException, JsonMappingException, IOException {
		
		new ObjectMapper().readValue(new File(this.name), TestModel.class);
		
		
	}

}
