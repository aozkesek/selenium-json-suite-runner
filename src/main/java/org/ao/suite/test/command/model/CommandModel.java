package org.ao.suite.test.command.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommandModel implements Cloneable {
	
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
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}

	@Override
	public CommandModel clone( ) {
		CommandModel cloned = new CommandModel();
		cloned.command = command;
		
		if (args != null && args.length > 0) {
			cloned.args = new String[args.length];
			for (int i = 0; i < args.length; i++)
				cloned.args[i] = args[i];
		}
		
		if (value != null)
			cloned.value = value;
		
		return cloned;
	}
}
