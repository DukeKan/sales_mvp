/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.presentes.impl;


import com.company.sales.entity.Customer;
import com.company.sales.entity.Order;
import com.company.sales.gui.customer.CustomerEdit;
import com.company.sales.gui.customer.CustomerEditScreen;
import com.company.sales.mvp.builders.*;
import com.company.sales.mvp.models.impl.CustomerModelImpl;
import com.company.sales.mvp.models.interfaces.CustomerModel;
import com.company.sales.mvp.presentes.interfaces.Presenter;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.CollectionDatasource.CollectionChangeEvent;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeEvent;

import java.util.UUID;

import static com.haulmont.cuba.gui.data.CollectionDatasource.Operation.ADD;
import static com.haulmont.cuba.gui.data.CollectionDatasource.Operation.REMOVE;

/**
 * Created by DukeKan on 13.10.2017.
 */
public class CustomerPresenterImpl extends AbstractPresenter {
    private CustomerEditScreen screen;
    private CustomerModel model;

    public CustomerPresenterImpl(CustomerEdit screen, CustomerModelImpl model) {
        this.screen = screen;
        this.model = model;
        init();
    }

    private void init() {
        ListenerBuilder.buildPropertyListener(Customer.class, this)
                .setDatasource(screen.getCustomerDs())
                .setValidationHandler(this::validateCustomerPropertyChange)
                .setAfterNonSuccessValidationHandler((event) ->
                        setValueIgnoreListeners(event.getDs(), event.getProperty(), event.getPrevValue()))
                .build();

        ListenerBuilder.buildCollectionChangeListener(Order.class, this)
                .setDatasource(screen.getOrdersDs())
                .setValidationHandler(this::validateOrderCollectionChange)
                .setAfterSuccessValidationHandler(this::processOrdersCollectionChange)
                .setAfterNonSuccessValidationHandler((event) ->
                        processItemsIgnoreListeners(event.getDs(), event.getItems(), ADD))
                .build();

        screen.addCalculateBtnListener(this::onCalculatePressed);
    }

    private void onCalculatePressed(Customer customer) {
        int ordersAmount = model.getOrdersAmount(customer);
        screen.showAmountSum(ordersAmount);
    }

    public void validateCustomerPropertyChange(ItemPropertyChangeEvent<Customer> event) throws ValidationException {
        if (event.getProperty().equals("name") && !"Test".equals(event.getValue())) {
            screen.showIncorrectNameNotification();
            throw new ValidationException("Incorrect data");
        }
    }

    public void validateOrderCollectionChange(CollectionChangeEvent<Order, UUID> event) throws ValidationException {
        if (event.getOperation().equals(REMOVE)) {
            if (model.allOrdersAreZero(event.getItems())) {
                screen.showIncorrectOrderAmountNotification();
                throw new ValidationException("Orders amount!");
            }
        }
    }

    private void processOrdersCollectionChange(CollectionChangeEvent<Order, UUID> event) {
        if (event.getOperation().equals(REMOVE)) {
            processItemsIgnoreListeners(event.getDs(), event.getItems(), event.getOperation());
        }
    }
}
