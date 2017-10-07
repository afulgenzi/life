package com.fulg.life.service.exception;

public class UnauthorizedOperationException extends Exception {

	public UnauthorizedOperationException(String username) {
		super("User '" + username + "' is unauthorized to execute this operation.");
	}
}
