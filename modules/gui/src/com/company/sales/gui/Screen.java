/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.gui;

import com.company.sales.entity.Order;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by DukeKan on 15.10.2017.
 */
public interface Screen<E extends Entity<UUID>> {

    default void setPropertyValue(Datasource<E> datasource, String propertyName, Object value) {
        datasource.getItem().setValue(propertyName, value);
    }

    default <P extends Entity<UUID>> void safeRemoveItemsFromDatasource(CollectionDatasource<P, UUID> datasource, Collection<P> items) {
        Collection<P> copy = new HashSet<>(items);
        copy.forEach(datasource::removeItem);
    }
}
