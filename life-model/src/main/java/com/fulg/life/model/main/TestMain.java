package com.fulg.life.model.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMain {
    private static final Logger LOG = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) {
        String values = "1, 2, 3";
        String[] splittedValues = values.split(",");
    }
}