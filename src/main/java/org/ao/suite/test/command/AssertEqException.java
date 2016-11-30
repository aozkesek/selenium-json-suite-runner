package org.ao.suite.test.command;

public class AssertEqException extends RuntimeException {
	
	public AssertEqException(String message) {
		super(message);
	}
	
	public AssertEqException(String message, Throwable e) {
		super(message, e);
	}
}
