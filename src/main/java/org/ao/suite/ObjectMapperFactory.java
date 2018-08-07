package org.ao.suite;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

@Component
@Scope("singleton")
public class ObjectMapperFactory {
	
	@Autowired
	private SuiteProperty suiteProperty;
	
	@PostConstruct
	public void init() {
		if (suiteProperty == null)
			throw new NullPointerException("SuiteProperty");
	}
	
	public ObjectMapper getObjectMapper() {
		if (suiteProperty != null && suiteProperty.format.equals("yaml"))
			return new ObjectMapper(new YAMLFactory());
		//return default one if someone call this method directly instead of by getBean
		return new ObjectMapper();
	}
}
