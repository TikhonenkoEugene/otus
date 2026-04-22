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
    int allTestsCounter;
    int failTestsCounter;

    public Application(Class<?>... classArr) {
        this.classArr = classArr;
        this.allTestsCounter = 0;
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

    private List<Method> getAllMethodsOrdered(Class<?> clazz) {
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        List<Method> afretMethods = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            }
            else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
            else if (method.isAnnotationPresent(After.class)) {
                afretMethods.add(method);
            }
        }
        List<Method> allMethods = new ArrayList<>();
        allMethods.addAll(beforeMethods);
        allMethods.addAll(testMethods);
        allMethods.addAll(afretMethods);
        return allMethods;
    }

    private void executeClass(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        List<Method> classMethods = getAllMethodsOrdered(clazz);
        System.out.println(clazz.getAnnotation(Suite.class).value());
        var instance = clazz.getConstructor().newInstance();
        for (Method method : classMethods) {
            try {
                if (method.isAnnotationPresent(Before.class)) {
                    System.out.println(method.getAnnotation(Before.class).value());
                }
                else if (method.isAnnotationPresent(Test.class)) {
                    System.out.println(method.getAnnotation(Test.class).value());
                    allTestsCounter++;
                }
                else if (method.isAnnotationPresent(After.class)) {
                    System.out.println(method.getAnnotation(After.class).value());
                }
                method.invoke(instance);
            }
            catch (Exception exception) {
                failTestsCounter++;
                System.out.println("\u001B[31m" + "Result: Filed" + "\u001B[0m");
            }
        }
        System.out.println(LINE);
    }

    private void printCountedTests() {
        System.out.println("Total test:   " + allTestsCounter);
        System.out.println("Passed test:  " + (allTestsCounter - failTestsCounter));
        System.out.println("Failed tests: " + failTestsCounter);
        System.out.println(LINE);
    }

}
