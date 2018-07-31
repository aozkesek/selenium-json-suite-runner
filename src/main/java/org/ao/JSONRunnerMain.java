package org.ao;

import java.io.IOException;
import org.ao.suite.SuiteDriver;
import org.ao.suite.SuiteProperty;
import org.ao.suite.SuiteTask;
import org.ao.suite.test.command.exception.CommandNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class JSONRunnerMain {

	public static Logger AppLogger = LoggerFactory.getLogger(JSONRunnerMain.class);

	public static ApplicationContext AppContext;	
	public static SuiteProperty SuiteProp;
	
	public static void main(String[] args) {

		AppContext = SpringApplication.run(JSONRunnerMain.class, args);
		SuiteProp = AppContext.getBean(SuiteProperty.class);
		
		if (SuiteProp.isParallel && SuiteProp.parallelCount > 1)
			parallelRun();	
		else
			run();
			
		int exitCode = SpringApplication.exit(AppContext);
		if (exitCode != 0)
			AppLogger.error("Application exit code is {}.", exitCode);
		
	}

	public static void run() {
		SuiteDriver startUpSuite = AppContext.getBean(SuiteDriver.class);
		try {
			startUpSuite.Load(SuiteProp.startUp);
			AppLogger.debug("startup suite is loaded.");
			startUpSuite.RunTests();

		} catch (IOException | CommandNotFoundException e) {
			AppLogger.error("Program interrupted by {}", e);
			
		}
		finally {
			startUpSuite.destroy(); // !!! re-check this method's execution
		}
	}

	public static void parallelRun() {
		AppLogger.debug("Parallel Mode is ON.");
		List<SuiteTask> suiteTasks = new ArrayList<SuiteTask>();
		
		for (int i = 0; i < SuiteProp.parallelCount; i++) 
			suiteTasks.add(new SuiteTask(AppContext.getBean(SuiteDriver.class), SuiteProp.startUp));
			
		ExecutorService suiteExec = Executors.newFixedThreadPool(SuiteProp.parallelCount);
		try {
			suiteExec.invokeAll(suiteTasks);		
			suiteExec.wait();
		} catch (InterruptedException e) {
			AppLogger.error("Program interrupted by {}", e);
		}
		
		suiteExec.shutdown();
		try {
			suiteExec.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			suiteExec.shutdownNow();
			try {
				suiteExec.awaitTermination(1, TimeUnit.MINUTES);
			} catch (InterruptedException e1) {
				
			}
		}
			
		AppLogger.debug("Suits finished in parallel.");
	}

}
