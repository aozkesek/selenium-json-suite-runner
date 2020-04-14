package org.ao.suite;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperFactory {
	
	@Autowired
	private SuiteProperties suiteProperties;
	
	@PostConstruct
	public void init() {
		if (suiteProperties == null) {
            throw new NullPointerException("suite-properties");
        }
	}
	
	public ObjectMapper getObjectMapper() {
        
		if (suiteProperties.format.equals("yaml")) {
            return new ObjectMapper(new YAMLFactory());
        }
		//return default one if someone call this method directly instead of by getBean
		return new ObjectMapper();
	}
}
