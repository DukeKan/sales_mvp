/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.builders;

import com.company.sales.mvp.presentes.impl.AbstractPresenter;
import com.company.sales.mvp.presentes.interfaces.Presenter;
import com.company.sales.mvp.validators.ItemPropertyChangeValidator;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeEvent;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by DukeKan on 01.11.2017.
 */
public class ItemPropertyChangeListenerBuilder<E extends Entity<UUID>>
        implements DatasourceListenerBuilder<E, Datasource<E>, ItemPropertyChangeValidator<E>, ItemPropertyChangeEvent<E>> {

    private AbstractPresenter presenter = null;

    private Datasource<E> datasource;
    private ItemPropertyChangeValidator<E> validationHandler;
    private Consumer<ItemPropertyChangeEvent<E>> afterSuccessValidationHandler;
    private Consumer<ItemPropertyChangeEvent<E>> afterNonSuccessValidationHandler;
    private Consumer<ItemPropertyChangeEvent<E>> anywayDoneHandler;

    ItemPropertyChangeListenerBuilder(AbstractPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public ItemPropertyChangeListenerBuilder<E> setDatasource(Datasource<E> datasource) {
        this.datasource = datasource;

        return this;
    }

    @Override
    public ItemPropertyChangeListenerBuilder<E> setValidationHandler(ItemPropertyChangeValidator<E> validationHandler) {
        this.validationHandler = validationHandler;

        return this;
    }

    @Override
    public ItemPropertyChangeListenerBuilder<E> setAfterSuccessValidationHandler(Consumer<ItemPropertyChangeEvent<E>> afterSuccessValidationHandler) {
        this.afterSuccessValidationHandler = afterSuccessValidationHandler;

        return this;
    }

    @Override
    public ItemPropertyChangeListenerBuilder<E> setAfterNonSuccessValidationHandler(Consumer<ItemPropertyChangeEvent<E>> afterNonSuccessValidationHandler) {
        this.afterNonSuccessValidationHandler = afterNonSuccessValidationHandler;

        return this;
    }

    @Override
    public ItemPropertyChangeListenerBuilder<E> setAnywayDoneHandler(Consumer<ItemPropertyChangeEvent<E>> anywayDoneHandler) {
        this.anywayDoneHandler = anywayDoneHandler;

        return this;
    }

    @Override
    public void build() {
        if (datasource != null) {
            Datasource.ItemPropertyChangeListener<E> listener = event -> {
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
            };
            datasource.addItemPropertyChangeListener(listener);
            presenter.addItemPropertyChangeListener(datasource, listener);
        } else {
            throw new NullPointerException("Datasource is not provided");
        }
    }
}
