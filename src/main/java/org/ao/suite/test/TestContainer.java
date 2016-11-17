package org.ao.suite.test;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class TestContainer {

	private static ConcurrentHashMap<String, TestDriver> testDrivers;
	private static ConcurrentHashMap<String, Object> variables;
	
	@PostConstruct
	public void init() {
		testDrivers = new ConcurrentHashMap<String, TestDriver>();
		variables = new ConcurrentHashMap<String, Object>();
	}
	
	public void putTestDriver(String key, TestDriver testDriver) {
		testDrivers.put(key, testDriver);
		
	}
	
	public TestDriver getTestDriver(String key) {
		return testDrivers.get(key);
	}
	
	public boolean containsTestDriverKey(String key) {
		return testDrivers.containsKey(key);
	}

	public void putVariable(String key, Object object) {
		variables.put(key, object);
		
	}
	
	public Object getVariable(String key) {
		return variables.get(key);
	}
	
	public boolean containsVariablesKey(String key) {
		return variables.containsKey(key);
	}
}
