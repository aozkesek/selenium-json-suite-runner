package org.ao.suite.test;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TestContainer {

	private Map<String, TestDriver> testDrivers;
	
	@PostConstruct
	public void init() {
		testDrivers = new HashMap<>();
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

	
}
