/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.builders;

import com.company.sales.mvp.validators.CollectionChangeValidator;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.CollectionDatasource.CollectionChangeEvent;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by DukeKan on 02.11.2017.
 */
public class CollectionChangeListenerBuilder<E extends Entity<UUID>>
        implements DatasourceListenerBuilder<E, CollectionDatasource<E, UUID>, CollectionChangeValidator<E>, CollectionChangeEvent<E, UUID>> {

    private CollectionDatasource<E, UUID> datasource;
    private CollectionChangeValidator<E> validationHandler;
    private Consumer<CollectionChangeEvent<E, UUID>> afterSuccessValidationHandler;
    private Consumer<CollectionChangeEvent<E, UUID>> afterNonSuccessValidationHandler;
    private Consumer<CollectionChangeEvent<E, UUID>> anywayDoneHandler;

    CollectionChangeListenerBuilder(){}

    @Override
    public CollectionChangeListenerBuilder<E> setDatasource(CollectionDatasource<E, UUID> datasource) {
        this.datasource = datasource;
        return this;
    }

    @Override
    public CollectionChangeListenerBuilder<E> setValidationHandler(CollectionChangeValidator<E> validationHandler) {
        this.validationHandler = validationHandler;

        return this;
    }

    @Override
    public CollectionChangeListenerBuilder<E> setAfterSuccessValidationHandler(Consumer<CollectionChangeEvent<E, UUID>> afterSuccessValidationHandler) {
        this.afterSuccessValidationHandler = afterSuccessValidationHandler;

        return this;
    }

    @Override
    public CollectionChangeListenerBuilder<E> setAfterNonSuccessValidationHandler(Consumer<CollectionChangeEvent<E, UUID>> afterNonSuccessValidationHandler) {
        this.afterNonSuccessValidationHandler = afterNonSuccessValidationHandler;

        return this;
    }

    @Override
    public CollectionChangeListenerBuilder<E> setAnywayDoneHandler(Consumer<CollectionChangeEvent<E, UUID>> anywayDoneHandler) {
        this.anywayDoneHandler = anywayDoneHandler;

        return this;
    }

    @Override
    public void build() {
        if (datasource != null) {
            datasource.addCollectionChangeListener(event -> {
                if (anywayDoneHandler != null) {
                    anywayDoneHandler.accept(event);
                }
                try {
                    if (validationHandler != null) {
                        validationHandler.validate(event);
                    }
                    if (afterSuccessValidationHandler != null) {
                        afterSuccessValidationHandler.accept(event);
                    }
                } catch (ValidationException e) {
                    if (afterNonSuccessValidationHandler != null) {
                        afterNonSuccessValidationHandler.accept(event);
                    }
                }
            });
        } else {
            throw new NullPointerException("Datasource is not provided");
        }
    }
}
