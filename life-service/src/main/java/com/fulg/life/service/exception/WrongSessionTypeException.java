package com.fulg.life.service.exception;

public class WrongSessionTypeException extends RuntimeException {

	public WrongSessionTypeException(Object actualObject, Class expectedClass) {
		super("Wrong object type in session. Expected '" + expectedClass.getName() + "' but found '"
				+ actualObject.getClass().getName() + "'");
	}
}
