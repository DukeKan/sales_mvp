/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.presentes.interfaces;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.CollectionDatasource.CollectionChangeEvent;
import com.haulmont.cuba.gui.data.CollectionDatasource.CollectionChangeListener;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeEvent;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeListener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by DukeKan on 13.10.2017.
 */
public interface Presenter {

    @FunctionalInterface
    interface Validator<T> {
        void validate(T t) throws ValidationException;
    }

    Set<ItemPropertyChangeListener> propertyChangeListeners = new HashSet<>();
    Set<CollectionChangeListener> collectionChangeListeners = new HashSet<>();

    default <E extends Entity<UUID>> void registerPropertyChangeListener(Datasource<E> datasource,
                                                Validator<ItemPropertyChangeEvent<E>> validation,
                                                Consumer<ItemPropertyChangeEvent<E>> afterSuccessValidation,
                                                Consumer<ItemPropertyChangeEvent<E>> afterNonSuccessValidation){
        ItemPropertyChangeListener<E> listener = event -> {
            try {
                validation.validate(event);
                afterSuccessValidation.accept(event);
            } catch (ValidationException e) {
                afterNonSuccessValidation.accept(event);
            }
        };
        datasource.addItemPropertyChangeListener(listener);
        propertyChangeListeners.add(listener);
    }

    default <E extends Entity<UUID>> void registerCollectionChangeListener(CollectionDatasource<E, UUID> datasource,
                                                  Validator<CollectionChangeEvent<E, UUID>> validation,
                                                  Consumer<CollectionChangeEvent<E, UUID>> afterSuccessValidation,
                                                  Consumer<CollectionChangeEvent<E, UUID>> afterNonSuccessValidation){
        CollectionChangeListener<E, UUID> listener = event -> {
            try {
                validation.validate(event);
                afterSuccessValidation.accept(event);
            } catch (ValidationException e) {
                afterNonSuccessValidation.accept(event);
            }
        };
        datasource.addCollectionChangeListener(listener);
        collectionChangeListeners.add(listener);
    }

    default <E extends Entity<UUID>> void disablePropertyChangeListeners(Datasource<E> datasource){
        propertyChangeListeners.forEach(datasource::removeItemPropertyChangeListener);
    }

    default <E extends Entity<UUID>> void enablePropertyChangeListeners(Datasource<E> datasource){
        propertyChangeListeners.forEach(datasource::addItemPropertyChangeListener);
    }

    default <E extends Entity<UUID>> void disableCollectionChangeListeners(CollectionDatasource<E, UUID> datasource) {
        collectionChangeListeners.forEach(datasource::removeCollectionChangeListener);
    }

    default <E extends Entity<UUID>> void enableCollectionChangeListeners(CollectionDatasource<E, UUID> datasource) {
        collectionChangeListeners.forEach(datasource::addCollectionChangeListener);
    }
}
