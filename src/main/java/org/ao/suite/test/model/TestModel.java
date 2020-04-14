package org.ao.suite.test.model;

import java.util.List;
import java.util.Map;

import org.ao.suite.Model;
import org.ao.suite.test.command.model.CommandModel;

public class TestModel implements Model {

	private String name;
	private Map<String, String> arguments;
	private Map<String, String> vars;
	private List<CommandModel> commands;
	
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
	
	public List<CommandModel> getCommands() {
		return commands;
	}
	
	public void setCommands(List<CommandModel> commands) {
		this.commands = commands;
	}
	
}
