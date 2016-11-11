package org.ao.suite;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SuiteProperty {

	@Value("${suite.parallel}")
	public boolean isParallel;
	
	@Value("${suite.startup}")
	public String startUp;
	
	@Value("${suite.home}")
	public String home;
	
	@Value("${suite.test.home}")
	public String testHome;
	
	@Value("${suite.objectrepository.home}")
	public String objectRepositoryHome;
	
	@Value("${suite.webdriver}")
	public String webDriver;
	
}
