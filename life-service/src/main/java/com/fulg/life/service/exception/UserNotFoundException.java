package com.fulg.life.service.exception;

public class UserNotFoundException extends Exception {

	public UserNotFoundException(String username) {
		super("Cannot find user '" + username + "'");
	}
}
