package org.ao.suite.test;

import java.util.LinkedHashMap;

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
	
}
