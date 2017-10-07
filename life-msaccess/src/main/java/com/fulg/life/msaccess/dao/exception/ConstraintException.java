package com.fulg.life.msaccess.dao.exception;

public class ConstraintException extends Exception {

    public ConstraintException(String message) {
        super(message);
    }

    public ConstraintException(String message, Throwable ex) {
        super(message, ex);
    }
}
