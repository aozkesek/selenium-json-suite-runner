package org.ao.suite.model;

import java.util.LinkedHashMap;

import org.ao.suite.ObjectMapperFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

public class SuiteTestModel {

	@JsonProperty("file_name")
	private String fileName;
	
	private LinkedHashMap<String, Object> arguments;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public LinkedHashMap<String, Object> getArguments() {
		return arguments;
	}
	
	public void setArguments(LinkedHashMap<String, Object> arguments) {
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
