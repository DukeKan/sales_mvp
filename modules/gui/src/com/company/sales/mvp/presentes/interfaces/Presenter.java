/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.presentes.interfaces;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.CollectionDatasource.CollectionChangeEvent;
import com.haulmont.cuba.gui.data.CollectionDatasource.CollectionChangeListener;
import com.haulmont.cuba.gui.data.CollectionDatasource.Operation;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeEvent;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeListener;
import com.haulmont.cuba.gui.data.impl.AbstractDatasource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by DukeKan on 13.10.2017.
 */
public interface Presenter {

    default <E extends Entity<UUID>> void setValueIgnoreListeners(Datasource<E> ds, String propertyName, Object value) {
        ((AbstractDatasource) ds).enableListeners(false);
        setValue(ds, propertyName, value);
        ((AbstractDatasource) ds).enableListeners(true);
    }

    default <E extends Entity<UUID>> void processItemsIgnoreListeners(CollectionDatasource<E, UUID> ds, Collection<E> items,
                                                                      Operation operation) {
        ((AbstractDatasource) ds).enableListeners(false);
        processItems(ds, items, operation);
        ((AbstractDatasource) ds).enableListeners(true);
    }

    default <E extends Entity<UUID>> void setValue(Datasource<E> ds, String propertyName, Object value) {
        ds.getItem().setValueEx(propertyName, value);
    }

    default <E extends Entity<UUID>> void processItems(CollectionDatasource<E, UUID> ds, Collection<E> items,
                                                                      Operation operation) {
        switch (operation) {
            case ADD:
                items.forEach(ds::addItem);
                break;
            case REMOVE:
                items.forEach(ds::removeItem);
                break;
            default:
                throw new UnsupportedOperationException("Not implemented yet");
        }
    }
}
