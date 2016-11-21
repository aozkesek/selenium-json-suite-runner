package org.ao.suite.test.command;

public class CommandNotFoundException extends RuntimeException {
	
	public CommandNotFoundException(String message) {
		super(message);
	}
	
	public CommandNotFoundException(String message, Throwable e) {
		super(message, e);
	}
}
