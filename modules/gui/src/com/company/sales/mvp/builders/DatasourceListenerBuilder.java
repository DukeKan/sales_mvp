/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.builders;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.Datasource;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by DukeKan on 02.11.2017.
 */
interface DatasourceListenerBuilder<ENTITY extends Entity<UUID>, D extends  Datasource<ENTITY>, VALIDATOR, EVENT> {

    DatasourceListenerBuilder setDatasource(D datasource);
    DatasourceListenerBuilder setValidationHandler(VALIDATOR validationHandler);
    DatasourceListenerBuilder setAfterSuccessValidationHandler(Consumer<EVENT> afterSuccessValidationHandler);
    DatasourceListenerBuilder setAfterNonSuccessValidationHandler(Consumer<EVENT> afterNonSuccessValidationHandler);
    DatasourceListenerBuilder setAnywayDoneHandler(Consumer<EVENT> anywayDoneHandler);
    void build();
}
