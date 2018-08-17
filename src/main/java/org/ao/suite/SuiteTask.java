package org.ao.suite;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.ao.suite.test.command.exception.CommandNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuiteTask implements Callable<Boolean> {

	private static Logger logger = LoggerFactory.getLogger(SuiteDriver.class);
	
	private SuiteDriver suiteDriver;
	private String suite;
	
	public SuiteTask(SuiteDriver suiteDriver, String suite) {
		this.suiteDriver = suiteDriver;
		this.suite = suite;
		
	}
		
	public SuiteDriver getSuiteDriver() {
		return suiteDriver;
	}
	

	@Override
	public Boolean call() {
		try {
			suiteDriver.loadSuite(suite);
			logger.debug("{} suite is loaded.", suite);
			suiteDriver.RunTests();
		} catch (CommandNotFoundException | IOException e) {
			logger.error("Program interrupted by;", e);
		}

		return true;
	}

}
