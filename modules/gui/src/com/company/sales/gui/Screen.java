/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.gui;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.data.Datasource;

import java.util.UUID;

/**
 * Created by DukeKan on 15.10.2017.
 */
public interface Screen<E extends Entity<UUID>> {


    default void setPropertyValue(Datasource<E> datasource, String propertyName, Object value) {
        datasource.getItem().setValue(propertyName, value);
    }
}
