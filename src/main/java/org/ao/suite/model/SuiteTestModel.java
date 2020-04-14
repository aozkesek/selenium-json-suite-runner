package org.ao.suite.model;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.ao.suite.Model;

public class SuiteTestModel implements Model {

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

}
