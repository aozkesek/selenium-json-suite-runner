package org.ao.suite;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	
}
