/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.builders;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.Datasource;

import java.util.UUID;

/**
 * Created by DukeKan on 01.11.2017.
 */
@FunctionalInterface
public interface PropertyChangeValidator<E extends Entity<UUID>> {
    void validate(Datasource.ItemPropertyChangeEvent<E> e) throws ValidationException;
}
