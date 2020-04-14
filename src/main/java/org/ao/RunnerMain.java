package org.ao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.ao.suite.SuiteDriver;
import org.ao.suite.SuiteProperties;
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
@ComponentScan(basePackages="org.ao.suite")
@PropertySource("classpath:application.yml")
public class RunnerMain implements CommandLineRunner {

	@Autowired
	private ApplicationContext contex;
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

        final List<Future<Boolean>> suitesReturn = new ArrayList<>();
        
        logger.info("This run is parallel({}) and {} threads.", 
            suiteProperties.isParallel, suiteProperties.threadCount);

		if (suiteProperties.isParallel && suiteProperties.threadCount > 1) {

			suiteProperties.suites.forEach(s -> {
                for(int i = 0; i < suiteProperties.threadCount; i++) {
                    logger.info("Suite {} is being prepared...", s);
                    suitesReturn.add(
                        executor.submit(contex.getBean(SuiteDriver.class, s))
                        );
                }
            });
		}
		else {

            logger.info("Suite {} is being prepared...", suiteProperties.suites.get(0));
			suitesReturn.add(
                executor.submit(
                    contex.getBean(SuiteDriver.class, suiteProperties.suites.get(0))
                    )
                );
		}


	}
}
