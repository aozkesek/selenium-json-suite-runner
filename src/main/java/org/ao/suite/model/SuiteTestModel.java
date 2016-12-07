package org.ao.suite.model;

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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("file_name=").append(fileName).append(", ");
		
		sb.append("arguments={");
		if (arguments != null)
			arguments.forEach((k, v) -> sb.append(k).append("=").append(v).append(", "));
		sb.append("}");
		
		return sb.toString();
	}
}
