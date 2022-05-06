package org.ao.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuiteModel implements Model {

	private String name;
	@JsonProperty("object_repository")
	private String objectRepository;
	@JsonProperty("test_path")
	private String testPath;
	@JsonProperty("test_url")
	private String testUrl;
	private List<SuiteTestModel> tests;
	
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
    
	public List<SuiteTestModel> getTests() {
		return tests;
    }
    
	public void setTests(List<SuiteTestModel> tests) {
		this.tests = tests;
	}
	
}
