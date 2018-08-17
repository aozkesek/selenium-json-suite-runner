package org.ao.suite.test.command.model;

import org.ao.suite.ObjectContainer;

public class CommandModelBuilder {
	private String command;
	private String[] args;
	private Object value;
	
	public CommandModelBuilder setCommand(String command) {
		this.command = command;
		return this;
	}
	
	public CommandModelBuilder setValue(Object value) {
		this.value = value;
		return this;
	}
	
	public CommandModelBuilder setValue(Object value, ObjectContainer objectContainer) {
		if (value == null)
			return this;
		if (value instanceof String)
			this.value = objectContainer.getReplacedVariable(String.valueOf(value));
		else
			this.value = value;
		return this;
	}
	
	public CommandModelBuilder setArgs(String[] args) {
		if (args == null)
			return this;
		
		this.args = new String[args.length];
		for(int i = 0; i < args.length; i++)
			this.args[i] = args[i];
		
		return this;
	}
	
	public CommandModelBuilder setArgs(String[] args, ObjectContainer objectContainer) {
		if (args == null)
			return this;
		
		this.args = new String[args.length];
		for(int i = 0; i < args.length; i++)
			this.args[i] = objectContainer.getReplacedVariable(args[i]);
		
		return this;
	}
	
	public CommandModel build() {
		CommandModel commandModel = new CommandModel();
		commandModel.setArgs(args);
		commandModel.setCommand(command);
		commandModel.setValue(value);
		return commandModel;
	}
}
