package org.ao.suite.test.command;

public class CommandModel {
	
	private String command;
	private String args;
	private String value;
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("command=").append(command).append(", ");
		sb.append("args=").append(args).append(", ");
		sb.append("value=").append(value);
		
		return sb.toString();
	}
	

}
