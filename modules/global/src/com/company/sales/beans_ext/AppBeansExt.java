/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.beans_ext;

import com.haulmont.cuba.core.global.AppBeans;

import javax.annotation.Nullable;

/**
 * Created by DukeKan on 05.11.2017.
 */
public class AppBeansExt extends AppBeans {

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> beanType) {
        T t = AppBeans.get(beanType);
        return new BeanProxy<T>(t, (Class<T>[]) t.getClass().getInterfaces()).proxy();
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String name) {
        T t = AppBeans.get(name);
        return new BeanProxy<T>(t, (Class<T>[]) t.getClass().getInterfaces()).proxy();
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String name, @Nullable Class<T> beanType) {
        T t = AppBeans.get(name, beanType);
        return new BeanProxy<T>(t, (Class<T>[]) t.getClass().getInterfaces()).proxy();
    }
}
