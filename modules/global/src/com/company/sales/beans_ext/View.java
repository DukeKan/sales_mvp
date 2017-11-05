/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.beans_ext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.haulmont.cuba.core.global.View.MINIMAL;

/**
 * Created by DukeKan on 05.11.2017.
 */
@Retention(value= RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface View {
    String name() default MINIMAL;
}
