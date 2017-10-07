package com.fulg.life.facade.strategy.impl;

/**
 * Created by alessandro.fulgenzi on 08/05/16.
 */
public class ConvertException extends RuntimeException {
    public ConvertException(final String msg)
    {
        super(msg);
    }
    public ConvertException(final String msg, final Exception ex)
    {
        super(msg, ex);
    }
}
