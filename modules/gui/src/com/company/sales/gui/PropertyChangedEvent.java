/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.gui;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.data.Datasource;

import java.util.UUID;

/**
 * Created by DukeKan on 16.10.2017.
 */
public class PropertyChangedEvent<E extends Entity<UUID>> {
    private String propertyName;
    private Object prevValue;
    private Object newValue;

    public PropertyChangedEvent(Datasource.ItemPropertyChangeEvent<E> sourceEvent) {
        this.propertyName = sourceEvent.getProperty();
        this.prevValue = sourceEvent.getPrevValue();
        this.newValue = sourceEvent.getValue();
    }

    public PropertyChangedEvent(String propertyName, Object prevValue, Object newValue) {
        this.propertyName = propertyName;
        this.prevValue = prevValue;
        this.newValue = newValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Object getPrevValue() {
        return prevValue;
    }

    public void setPrevValue(Object prevValue) {
        this.prevValue = prevValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }
}
