package org.ao.suite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope("prototype")
public class SuiteDriver {
	
	public String SuiteId;
	
	@Autowired
	private SuiteProperty suiteProp;
	
	private static Logger SuiteLogger = LoggerFactory.getLogger(SuiteDriver.class);
	private SuiteModel suiteModel;
	
	public void Load(String suitePathName) throws JsonParseException, IOException {
		String pathName;
		
		if (suiteProp.home.length() > 0) {
			if (suiteProp.home.endsWith("/") || suiteProp.home.endsWith("//"))
				pathName = suiteProp.home.concat(suitePathName);
			else
				pathName = suiteProp.home.concat("/").concat(suitePathName);
		}
		else
			pathName = suitePathName;
		
		if (!pathName.endsWith(".json"))
			pathName = pathName.concat(".json");
		
		suiteModel = new ObjectMapper().readValue(new File(pathName), SuiteModel.class);
		suiteModel.getTests()[0].getArguments().get("username");

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(suiteModel.toString());
		
		
		return sb.toString();
	}
}
