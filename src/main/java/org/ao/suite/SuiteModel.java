package org.ao.suite;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuiteModel {

	private String name;
	@JsonProperty("object_repository_path")
	private String objectRepositoryPath;
	@JsonProperty("test_path")
	private String testPath;
	private SuiteTestModel[] tests;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getObjectRepositoryPath() {
		return objectRepositoryPath;
	}
	public void setObjectRepositoryPath(String objectRepositoryPath) {
		this.objectRepositoryPath = objectRepositoryPath;
	}
	public String getTestPath() {
		return testPath;
	}
	public void setTestPath(String testPath) {
		this.testPath = testPath;
	}
	public SuiteTestModel[] getTests() {
		return tests;
	}
	public void setTests(SuiteTestModel[] tests) {
		this.tests = tests;
	}
	
}
