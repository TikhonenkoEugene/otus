package org.qadev;

import org.qadev.service.TestLogging;
import org.qadev.service.TestLoggingImpl;

public class Main {
    public static void main(String[] args) throws Exception {
        TestLogging testLogging = Demo.create(TestLogging.class, TestLoggingImpl.class);

        testLogging.calculation(5);
        testLogging.calculation(1, 9);
        testLogging.calculation(3, 7, "A");
        testLogging.calculation(-4);
        testLogging.calculation(8, 0);
    }

}