package org.ao;

import org.ao.suite.SuiteProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JSONRunnerMain {

	public static ApplicationContext AppContext;
	public static Logger AppLogger = LoggerFactory.getLogger(JSONRunnerMain.class);
	
	public static void main(String[] args) {

		AppContext = SpringApplication.run(JSONRunnerMain.class, args);

		SuiteProperty suiteProp = AppContext.getBean(SuiteProperty.class);
		
		if (suiteProp.isParallel) {
			AppLogger.debug("Parallel Mode is ON.");
		}

	}

}
