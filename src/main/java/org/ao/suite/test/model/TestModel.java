package org.ao.suite.test.model;

import java.util.Map;

import org.ao.suite.ObjectMapperFactory;
import org.ao.suite.test.command.model.CommandModel;

import com.fasterxml.jackson.core.JsonProcessingException;

/*
 * this class holds test definition that loaded from test script file
 */
public class TestModel {

	private String name;
	private Map<String, String> arguments;
	private Map<String, String> vars;
	private CommandModel[] commands;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, String> getArguments() {
		return arguments;
	}
	
	public void setArguments(Map<String, String> arguments) {
		this.arguments = arguments;
	}
	
	public Map<String, String> getVars() {
		return vars;
	}
	
	public void setVars(Map<String, String> vars) {
		this.vars = vars;
	}
	
	public CommandModel[] getCommands() {
		return commands;
	}
	
	public void setCommands(CommandModel[] commands) {
		this.commands = commands;
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
