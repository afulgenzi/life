package com.fulg.life.migration.exception;

public class PopulatorNotFoundException extends BusinessException {

    public PopulatorNotFoundException(String className) {
        super("Populator not found for class: " + className);
    }

    public PopulatorNotFoundException(String className, Throwable t) {
        super("Populator not found for class: " + className, t);
    }
}
