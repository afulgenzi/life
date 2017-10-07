package com.fulg.life.migration.exception;

public class BusinessException extends Exception {

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Throwable t) {
        super(msg, t);
    }
}
