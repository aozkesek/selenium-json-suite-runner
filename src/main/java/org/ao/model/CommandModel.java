package org.ao.model;

import java.util.List;

import org.ao.model.Model;

public class CommandModel implements Model {
	
	private String command;
	private List<String> args;
	private Object value;
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public List<String> getArgs() {
		return args;
	}
	
	public void setArgs(List<String> args) {
		this.args = args;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
}
