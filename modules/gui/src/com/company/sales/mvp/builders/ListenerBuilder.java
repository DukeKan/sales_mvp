/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.builders;

import com.haulmont.cuba.core.entity.Entity;

import java.util.UUID;

/**
 * Created by DukeKan on 02.11.2017.
 */
public class ListenerBuilder {
    public static <E extends Entity<UUID>> ItemPropertyChangeListenerBuilder<E> buildPropertyListener(Class<E> e) {
        return new ItemPropertyChangeListenerBuilder<>();
    }

    public static <E extends Entity<UUID>> CollectionChangeListenerBuilder<E> buildCollectionChangeListener(Class<E> e) {
        return new CollectionChangeListenerBuilder<>();
    }
}
