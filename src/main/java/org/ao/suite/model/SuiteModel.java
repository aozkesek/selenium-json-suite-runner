package org.ao.suite.model;

import org.ao.suite.ObjectMapperFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/*
 * this class holds suite definition that loaded from user suite script file
 */
public class SuiteModel {

	private String name;
	@JsonProperty("object_repository")
	private String objectRepository;
	@JsonProperty("test_path")
	private String testPath;
	@JsonProperty("test_url")
	private String testUrl;
	private SuiteTestModel[] tests;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getObjectRepository() {
		return objectRepository;
	}
	public void setObjectRepository(String objectRepository) {
		this.objectRepository = objectRepository;
	}
	public String getTestPath() {
		return testPath;
	}
	public void setTestPath(String testPath) {
		this.testPath = testPath;
	}
	public String getTestUrl() {
		return testUrl;
	}
	public void setTestUrl(String testUrl) {
		this.testUrl = testUrl;
	}
	public SuiteTestModel[] getTests() {
		return tests;
	}
	public void setTests(SuiteTestModel[] tests) {
		this.tests = tests;
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
