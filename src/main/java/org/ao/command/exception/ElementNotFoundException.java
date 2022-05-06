package org.ao.command.exception;

public class ElementNotFoundException extends RuntimeException {

	public ElementNotFoundException(String message) {
		super(message);
	}
	
	public ElementNotFoundException(String message, Throwable exception) {
		super(message, exception);
	}
}
