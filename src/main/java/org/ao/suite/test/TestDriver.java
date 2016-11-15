package org.ao.suite.test;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Component;

@Component
public class TestDriver {
	
	
	private String name;
	private LinkedHashMap<String, Object> arguments;
	
	public TestDriver(String name, LinkedHashMap<String, Object> arguments) {
		
		this.name = name;
		this.arguments = arguments;
		
	}
	
	public void Load() {
		
	}

}
