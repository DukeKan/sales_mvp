/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.beans_ext;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.PersistenceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;

import static java.lang.String.format;

/**
 * Created by DukeKan on 05.11.2017.
 */
public class BeanProxy<T> implements InvocationHandler {
    private DataManager dataManager = AppBeans.get(DataManager.NAME);
    private Logger log = LoggerFactory.getLogger(BeanProxy.class);

    private final Class<T>[] interfaces;
    private T impl;

    public BeanProxy(T impl, Class<T>[] interfaces) {
        this.impl = impl;
        this.interfaces = interfaces;
    }

    @SuppressWarnings("unchecked")
    public T proxy() {
        return (T) Proxy.newProxyInstance(impl.getClass().getClassLoader(), interfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        Method implMethod = impl.getClass().getMethod(m.getName(), m.getParameterTypes());
        Parameter[] parameters = implMethod.getParameters();

        Annotation[][] parameterAnnotations = m.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg != null && arg instanceof Entity && PersistenceHelper.isDetached(arg)) {
                Annotation[] paramAnnotations = parameterAnnotations[i];
                Parameter parameter = parameters[i];
                for (Annotation annotation : paramAnnotations) {
                    if (annotation instanceof View) {
                        String viewName = ((View) annotation).name();
                        args[i] = dataManager.reload((Entity) arg, viewName);
                        log.debug(format("Method '%s' parameter '%s' was reloaded with '%s' view",
                                m.getName(), parameter.getName(), viewName));
                    }
                }
            }
        }

        return implMethod.invoke(impl, args);
    }
}
