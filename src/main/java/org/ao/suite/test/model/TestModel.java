package org.ao.suite.test.model;

import java.util.LinkedHashMap;

import org.ao.suite.test.command.model.CommandModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestModel {

	private String name;
	private LinkedHashMap<String, Object> arguments;
	private LinkedHashMap<String, Object> vars;
	private CommandModel[] commands;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LinkedHashMap<String, Object> getArguments() {
		return arguments;
	}
	
	public void setArguments(LinkedHashMap<String, Object> arguments) {
		this.arguments = arguments;
	}
	
	public LinkedHashMap<String, Object> getVars() {
		return vars;
	}
	
	public void setVars(LinkedHashMap<String, Object> vars) {
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
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}
}
