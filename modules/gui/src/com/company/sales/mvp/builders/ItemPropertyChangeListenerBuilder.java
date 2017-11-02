/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.builders;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeEvent;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by DukeKan on 01.11.2017.
 */
public class ItemPropertyChangeListenerBuilder<E extends Entity<UUID>> {

    private Datasource<E> datasource;
    private PropertyChangeValidator<E> validationHandler;
    private Consumer<ItemPropertyChangeEvent<E>> afterSuccessValidationHandler;
    private Consumer<ItemPropertyChangeEvent<E>> afterNonSuccessValidationHandler;
    private Consumer<ItemPropertyChangeEvent<E>> anywayDoneHandler;

    public ItemPropertyChangeListenerBuilder setDatasource(Datasource<E> datasource) {
        this.datasource = datasource;

        return this; // использовать flow не получается, пока не понял почему
    }

    public ItemPropertyChangeListenerBuilder setValidationHandler(PropertyChangeValidator<E> validationHandler) {
        this.validationHandler = validationHandler;

        return this;
    }

    public ItemPropertyChangeListenerBuilder setAfterSuccessValidationHandler(Consumer<ItemPropertyChangeEvent<E>> afterSuccessValidationHandler) {
        this.afterSuccessValidationHandler = afterSuccessValidationHandler;

        return this;
    }

    public ItemPropertyChangeListenerBuilder setAfterNonSuccessValidationHandler(Consumer<ItemPropertyChangeEvent<E>> afterNonSuccessValidationHandler) {
        this.afterNonSuccessValidationHandler = afterNonSuccessValidationHandler;

        return this;
    }

    public ItemPropertyChangeListenerBuilder setAnywayDoneHandler(Consumer<ItemPropertyChangeEvent<E>> anywayDoneHandler) {
        this.anywayDoneHandler = anywayDoneHandler;

        return this;
    }

    public void build() {
        if (datasource != null) {
            datasource.addItemPropertyChangeListener(event -> {
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
