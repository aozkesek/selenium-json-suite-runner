package org.ao.suite.test.command;

public class CommandModel {
	
	private String command;
	private String args;
	private Object value;
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
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
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
