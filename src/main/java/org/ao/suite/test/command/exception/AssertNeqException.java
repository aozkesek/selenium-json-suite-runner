package org.ao.suite.test.command.exception;

public class AssertNeqException extends RuntimeException {
	
	public AssertNeqException(String message) {
		super(message);
	}
	
	public AssertNeqException(String message, Throwable e) {
		super(message, e);
	}
}
