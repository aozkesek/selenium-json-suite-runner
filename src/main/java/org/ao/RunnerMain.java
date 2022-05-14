package org.ao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@ComponentScan(basePackages="org.ao")
@PropertySource("classpath:application.yml")
public class RunnerMain implements CommandLineRunner {

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private SuiteProperties suiteProperties;
    @Autowired
    private ThreadPoolTaskExecutor executor;
    
	public static void main(String[] args) {
		SpringApplication.run(RunnerMain.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

        final Logger logger = LoggerFactory.getLogger(RunnerMain.class);

		if (null == suiteProperties) {
			throw new IllegalArgumentException("suite-property");
		}

		if (suiteProperties.suites == null || suiteProperties.suites.size() == 0) {
			throw new IllegalArgumentException("suite->suites");
		}

		int k = suiteProperties.suites.size() + 4;
		if (suiteProperties.isParallel)
				k *= suiteProperties.threadCount;

		executor.setCorePoolSize(k);

        final List<Future<Boolean>> taskReturns = Collections.synchronizedList(new ArrayList<>());
        
        logger.info("This run is {} and has {} threads.",
            suiteProperties.isParallel ? "parallel" : "", suiteProperties.threadCount);

		if (suiteProperties.isParallel && suiteProperties.threadCount > 1) {
			suiteProperties.suites.forEach(s -> {
                for(int i = 0; i < suiteProperties.threadCount; i++) {
                    logger.info("Suite {} is being prepared...", s);
					SuiteDriver task = applicationContext.getBean(SuiteDriver.class, s);
					Future<Boolean> ret = executor.submit(task);
                    taskReturns.add(ret);
                }
            });
		}
		else {
            logger.info("Suite {} is being prepared...", suiteProperties.suites.get(0));
			SuiteDriver task = applicationContext.getBean(SuiteDriver.class, suiteProperties.suites.get(0));
			Future<Boolean> ret = executor.submit(task);
			taskReturns.add(ret);
		}

		boolean allDone;
		do {
			allDone = true;
			for (var ret : taskReturns) {
				if (!ret.isDone()) {
					allDone = false;
					break;
				}
				Thread.yield();
			}
		} while (!allDone);

		System.exit(0);
	}
}
