package org.ao.suite.test;

public class CommandModel {
	
	private String command;
	private String target;
	private String value;
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("command=").append(command).append(", ");
		sb.append("target=").append(target).append(", ");
		sb.append("value=").append(value);
		
		return sb.toString();
	}
	

}
