/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.presentes.interfaces;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeEvent;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeListener;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by DukeKan on 13.10.2017.
 */
public interface Presenter<E extends Entity<UUID>> {

    @FunctionalInterface
    interface Validator<T> {
        void validate(T t) throws ValidationException;
    }

    Set<ItemPropertyChangeListener> propertyChangeListeners = new HashSet<>();

    default void registerPropertyValidation(Datasource<E> datasource,
                                            Validator<ItemPropertyChangeEvent<E>> validation,
                                            Consumer<ItemPropertyChangeEvent<E>> afterNonSuccessValidation){
        ItemPropertyChangeListener<E> listener = event -> {
            try {
                validation.validate(event);
            } catch (ValidationException e) {
                afterNonSuccessValidation.accept(event);
            }
        };
        datasource.addItemPropertyChangeListener(listener);
        propertyChangeListeners.add(listener);
    }

    default void disablePropertyChangeListeners(Datasource<E> datasource){
        propertyChangeListeners.forEach(datasource::removeItemPropertyChangeListener);
    }

    default void enablePropertyChangeListeners(Datasource<E> datasource){
        propertyChangeListeners.forEach(datasource::addItemPropertyChangeListener);
    }

}
