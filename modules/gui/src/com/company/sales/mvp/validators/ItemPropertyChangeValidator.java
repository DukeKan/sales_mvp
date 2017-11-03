/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.validators;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeEvent;

import java.util.UUID;

/**
 * Created by DukeKan on 01.11.2017.
 */
@FunctionalInterface
public interface ItemPropertyChangeValidator<E extends Entity<UUID>> {
    void validate(ItemPropertyChangeEvent<E> e) throws ValidationException;
}
