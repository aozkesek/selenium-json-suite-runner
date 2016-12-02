package org.ao.suite.test;

import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class TestContainer {

	private static ConcurrentHashMap<String, TestDriver> testDrivers;
	
	@PostConstruct
	public void init() {
		testDrivers = new ConcurrentHashMap<String, TestDriver>();
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
