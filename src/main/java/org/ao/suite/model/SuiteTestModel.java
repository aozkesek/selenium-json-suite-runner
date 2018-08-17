package org.ao.suite.model;
import java.util.Map;
import org.ao.suite.ObjectMapperFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/*
 * this class holds tests definition that loaded from user test script file
 */
public class SuiteTestModel {

	@JsonProperty("file_name")
	private String fileName;
	
	private Map<String, String> arguments;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Map<String, String> getArguments() {
		return arguments;
	}
	
	public void setArguments(Map<String, String> arguments) {
		this.arguments = arguments;
	}
	
	@Override
	public String toString() {
		try {
			return new ObjectMapperFactory().getObjectMapper()
					.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}
}
