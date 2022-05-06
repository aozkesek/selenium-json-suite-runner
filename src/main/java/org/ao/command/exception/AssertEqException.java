package org.ao.command.exception;

public class AssertEqException extends RuntimeException {
	
	public AssertEqException(String message) {
		super(message);
	}
	
	public AssertEqException(String message, Throwable e) {
		super(message, e);
	}
}
