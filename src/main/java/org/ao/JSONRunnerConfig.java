package org.ao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan(basePackages="org.ao")
@PropertySources(
		@PropertySource("classpath:application.properties")
		)
public class JSONRunnerConfig {


	
}
