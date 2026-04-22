package org.qadev.core;

public class Assertion {

    public static void success() {
        System.out.println("\u001B[32m" + "Result: Success" + "\u001B[0m");
    }

    public static void fail() throws Exception {
        throw new Exception();
    }
}
