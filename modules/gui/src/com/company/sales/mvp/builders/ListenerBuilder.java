/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.builders;

import com.company.sales.mvp.presentes.impl.AbstractPresenter;
import com.haulmont.cuba.core.entity.Entity;

import java.util.UUID;

/**
 * Created by DukeKan on 02.11.2017.
 */
public class ListenerBuilder {
    public static <E extends Entity<UUID>> ItemPropertyChangeListenerBuilder<E> buildPropertyListener(
            Class<E> e, AbstractPresenter presenter) {
        return new ItemPropertyChangeListenerBuilder<>(presenter);
    }

    public static <E extends Entity<UUID>> CollectionChangeListenerBuilder<E> buildCollectionChangeListener(
            Class<E> e, AbstractPresenter presenter) {
        return new CollectionChangeListenerBuilder<>(presenter);
    }
}
