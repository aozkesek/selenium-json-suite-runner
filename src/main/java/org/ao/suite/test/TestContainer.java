package org.ao.suite.test;

import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class TestContainer {

	private static ConcurrentHashMap<String, TestDriver> testDrivers;
	private static Logger logger = LoggerFactory.getLogger(TestContainer.class);
	
	@PostConstruct
	public void init() {
		testDrivers = new ConcurrentHashMap<String, TestDriver>();
	}
	
	public void putTestDriver(String key, TestDriver testDriver) {
		testDrivers.put(key, testDriver);
		logger.debug("put test {}", key);
	}
	
	public TestDriver getTestDriver(String key) {
		return testDrivers.get(key);
	}
	
	public boolean containsTestDriverKey(String key) {
		return testDrivers.containsKey(key);
	}

	
}
