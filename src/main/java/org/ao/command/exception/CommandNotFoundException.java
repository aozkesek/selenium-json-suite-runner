package org.ao.command.exception;

public class CommandNotFoundException extends RuntimeException {
	
	public CommandNotFoundException(String message) {
		super(message);
	}
	
	public CommandNotFoundException(String message, Throwable e) {
		super(message, e);
	}
}
