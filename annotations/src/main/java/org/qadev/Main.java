package org.qadev;

import org.qadev.core.Application;
import org.qadev.tests.MyTests1;
import org.qadev.tests.MyTests2;
import org.qadev.tests.MyTests3;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        Application application = new Application(
                MyTests1.class,
                MyTests2.class,
                MyTests3.class
        );
        application.run();
    }
}