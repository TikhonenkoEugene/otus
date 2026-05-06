package org.qadev.service;

import org.qadev.annotations.Log;

public class TestLoggingImpl implements TestLogging {

    @Log
    @Override
    public void calculation(int param1) {
        System.out.println("-");
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        System.out.println("-");
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("-");
    }
}
