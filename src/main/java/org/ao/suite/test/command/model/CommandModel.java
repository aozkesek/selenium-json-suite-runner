package org.ao.suite.test.command.model;

import org.ao.suite.ObjectMapperFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

/*
 * this class holds command, command arguments and return value 
 */
public class CommandModel {
	
	private String command;
	private String[] args;
	private Object value;
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public String[] getArgs() {
		return args;
	}
	
	public void setArgs(String[] args) {
		this.args = args;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
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
