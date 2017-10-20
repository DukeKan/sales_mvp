/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.presentes.interfaces;

import com.company.sales.gui.PropertyChangedEvent;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeListener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by DukeKan on 13.10.2017.
 */
public interface Presenter<E extends Entity<UUID>> {

    Set<ItemPropertyChangeListener> propertyChangeListeners = new HashSet<>();

    default void registerPropertyValidation(Datasource<E> datasource){
        ItemPropertyChangeListener<E> listener = event -> {
            try {
                validate(new PropertyChangedEvent<>(event));
                // сделать лямбой продолжение дествий если всё тип-топ
            } catch (ValidationException e) {
                // сделать лямбой
                setValue(event.getProperty(), event.getPrevValue());
            }
        };
        datasource.addItemPropertyChangeListener(listener);
        propertyChangeListeners.add(listener);
    }

    void validate(PropertyChangedEvent event) throws ValidationException;

    void setValue(String propertyName, Object value);

    default void disableListeners(Datasource<E> datasource){
        propertyChangeListeners.forEach(datasource::removeItemPropertyChangeListener);
    }

    default void enableListeners(Datasource<E> datasource){
        propertyChangeListeners.forEach(datasource::addItemPropertyChangeListener);
    }

}
