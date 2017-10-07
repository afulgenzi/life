package com.fulg.life.service.exception;

/**
 * Created by alessandro.fulgenzi on 26/06/16.
 */
public class UnrecognizedParameterValueException extends RuntimeException {
    public UnrecognizedParameterValueException(String parameterName, String parameterValue)
    {
        super("Unrecognized value [" + parameterValue + "] for parameter [" + parameterName + "]");
    }
}
