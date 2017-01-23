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
	
	@Value("${suite.tests.home}")
	public String testsHome;
	
	@Value("${suite.objects.home}")
	public String objectsHome;
	
	@Value("${suite.screenshots.home}")
	public String screenshotsHome;
	
	@Value("${suite.reports.home}")
        public String reportsHome;
	
	@Value("${suite.webdriver}")
	public String webDriver;
	
	@Value("${suite.webdriver.timeout}")
	public long timeOut;
	
	@Value("${suite.remote.url}")
	public String remoteUrl;
	
}
