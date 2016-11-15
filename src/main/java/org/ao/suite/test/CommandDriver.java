package org.ao.suite.test;

public class CommandDriver {

	private String name;
	private String target;
	private String value;
	
	private CommandDriver nextCommand;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CommandDriver getNextCommand() {
		return nextCommand;
	}

	public void setNextCommand(CommandDriver nextCommand) {
		this.nextCommand = nextCommand;
	}
	
}
