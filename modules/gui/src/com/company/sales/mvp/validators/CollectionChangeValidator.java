/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.validators;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.CollectionDatasource.CollectionChangeEvent;
import com.haulmont.cuba.gui.data.Datasource;

import java.util.UUID;

/**
 * Created by DukeKan on 02.11.2017.
 */
@FunctionalInterface
public interface CollectionChangeValidator<E extends Entity<UUID>> {
    void validate(CollectionChangeEvent<E, UUID> e) throws ValidationException;
}
