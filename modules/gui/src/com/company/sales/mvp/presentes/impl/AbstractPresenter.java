/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.presentes.impl;

import com.company.sales.mvp.presentes.interfaces.Presenter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.CollectionDatasource.CollectionChangeListener;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeListener;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by DukeKan on 05.11.2017.
 */
public class AbstractPresenter implements Presenter {
    private Multimap<Datasource, ItemPropertyChangeListener> propertyChangeListeners = ArrayListMultimap.create();
    private Multimap<Datasource, CollectionChangeListener> collectionChangeListeners = ArrayListMultimap.create();

    public void addItemPropertyChangeListener(Datasource ds, ItemPropertyChangeListener listener){
        propertyChangeListeners.get(ds).add(listener);
    }

    public void addCollectionChangeListener(Datasource ds, CollectionChangeListener listener) {
        collectionChangeListeners.get(ds).add(listener);
    }

    public <E extends Entity<UUID>> void setValueIgnoreListeners(Datasource<E> ds, String propertyName, Object value) {
        propertyChangeListeners.get(ds).forEach(ds::removeItemPropertyChangeListener);
        setValue(ds, propertyName, value);
        propertyChangeListeners.get(ds).forEach(ds::addItemPropertyChangeListener);
    }

    public <E extends Entity<UUID>> void processItemsIgnoreListeners(CollectionDatasource<E, UUID> ds, Collection<E> items,
                                                                      CollectionDatasource.Operation operation) {
        collectionChangeListeners.get(ds).forEach(ds::removeCollectionChangeListener);
        processItems(ds, items, operation);
        collectionChangeListeners.get(ds).forEach(ds::addCollectionChangeListener);
    }

    public <E extends Entity<UUID>> void setValue(Datasource<E> ds, String propertyName, Object value) {
        ds.getItem().setValueEx(propertyName, value);
    }

    public <E extends Entity<UUID>> void processItems(CollectionDatasource<E, UUID> ds, Collection<E> items,
                                                       CollectionDatasource.Operation operation) {
        Collection<E> copy = new HashSet<>(items);
        switch (operation) {
            case ADD:
                copy.forEach(ds::addItem);
                break;
            case REMOVE:
                copy.forEach(ds::removeItem);
                break;
            default:
                throw new UnsupportedOperationException("Not implemented yet");
        }
    }
}
