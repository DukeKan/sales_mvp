/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.gui;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by DukeKan on 15.10.2017.
 */
public interface Screen<E extends Entity<UUID>> {

    default void setPropertyValue(Datasource<E> datasource, String propertyName, Object value) {
        datasource.getItem().setValue(propertyName, value);
    }

    default <P extends Entity<UUID>> void processItems(CollectionDatasource<P, UUID> datasource, Collection<P> items,
                                                       CollectionDatasource.Operation operation) {
        Collection<P> copy = new HashSet<>(items);
        switch (operation) {
            case ADD:
                copy.forEach(datasource::addItem);
                break;
            case REMOVE:
                copy.forEach(datasource::removeItem);
                break;
            default:
                throw new UnsupportedOperationException("Not implemented yet");
        }
    }
}
