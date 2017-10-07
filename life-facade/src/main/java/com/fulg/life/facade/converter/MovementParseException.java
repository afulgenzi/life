package com.fulg.life.facade.converter;

/**
 * Created by alessandro.fulgenzi on 08/05/16.
 */
public class MovementParseException extends RuntimeException {
    public MovementParseException(final String message) {
        super(message);
    }
    public MovementParseException(final String message, final Exception ex) {
        super(message, ex);
    }
}
