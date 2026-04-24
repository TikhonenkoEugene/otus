package org.qadev.core;

import org.qadev.annotations.After;
import org.qadev.annotations.Before;
import org.qadev.annotations.Suite;
import org.qadev.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private final static String LINE = "--------------------------------------";
    private final Class<?>[] classArr;
    int passedTestsCounter;
    int failTestsCounter;

    public Application(Class<?>... classArr) {
        this.classArr = classArr;
        this.passedTestsCounter = 0;
        this.failTestsCounter = 0;
    }

    public void run() throws NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
        System.out.println(LINE);
        for (Class<?> clazz : classArr) {
            executeClass(clazz);
        }
        printCountedTests();
    }

    private void executeClass(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        List<Method> testMethods = getTestMethods(clazz);
        System.out.println(clazz.getAnnotation(Suite.class).value());
        var instance = clazz.getConstructor().newInstance();
        for (Method method : testMethods) {
            for (Method before : getBeforeMethods(clazz)) {
                try {
                    System.out.println(before.getAnnotation(Before.class).value());
                    before.invoke(instance);
                }
                catch (Exception exception) {
                    System.out.println("Exception in before: " + method.getName());
                }
            }
            try {
                System.out.println(method.getAnnotation(Test.class).value());
                method.invoke(instance);
                passedTestsCounter++;
            }
            catch (Exception exception) {
                failTestsCounter++;
                System.out.println("\u001B[31m" + "Result: Filed" + "\u001B[0m");
            }
            for (Method after : getAfterMethods(clazz)) {
                try {
                    System.out.println(after.getAnnotation(After.class).value());
                    after.invoke(instance);
                }
                catch (Exception exception) {
                    System.out.println("Exception in after: " + method.getName());
                }
            }
        }
        System.out.println(LINE);
    }

    private List<Method> getTestMethods(Class<?> clazz) {
        List<Method> result = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                result.add(method);
            }
        }
        return result;
    }

    private List<Method> getBeforeMethods(Class<?> clazz) {
        List<Method> result = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                result.add(method);
            }
        }
        return result;
    }

    private List<Method> getAfterMethods(Class<?> clazz) {
        List<Method> result = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(After.class)) {
                result.add(method);
            }
        }
        return result;
    }

    private void printCountedTests() {
        System.out.println("Total test:   " + (passedTestsCounter + failTestsCounter));
        System.out.println("Passed test:  " + passedTestsCounter);
        System.out.println("Failed tests: " + failTestsCounter);
        System.out.println(LINE);
    }

}
