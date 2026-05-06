package org.qadev;

import org.qadev.annotations.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Demo {
    private static final Logger logger = LoggerFactory.getLogger(Demo.class);
    private Demo() {}

    static <T> T create(Class<T> iFace, Class<? extends T> clazz) throws Exception {
        T implementation = clazz.getDeclaredConstructor().newInstance();
        InvocationHandler handler = new MyInvocationHandler(implementation);
        Object proxy = Proxy.newProxyInstance(
                iFace.getClassLoader(),
                new Class<?>[] { iFace },
                handler);
        return iFace.cast(proxy);
    }

    static class MyInvocationHandler<T> implements InvocationHandler {
        private final T instance;

        public MyInvocationHandler(T instance) {
            this.instance = instance;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(Log.class)) {
                logger.info("Executed method: {}, params: {}", method.getName(), args);
            }
            return method.invoke(instance, args);
        }

        @Override
        public String toString() {
            return "InvocationHandler {" + "class = " + instance.getClass() + '}';
        }
    }
}