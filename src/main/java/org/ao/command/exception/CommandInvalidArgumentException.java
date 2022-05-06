package org.ao.command.exception;

public class CommandInvalidArgumentException extends RuntimeException {
	
	public CommandInvalidArgumentException(String message) {
		super(message);
	}
	
	public CommandInvalidArgumentException(String message, Throwable e) {
		super(message, e);
	}
}
