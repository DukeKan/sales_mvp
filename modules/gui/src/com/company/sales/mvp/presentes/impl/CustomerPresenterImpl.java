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
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.CollectionDatasource.CollectionChangeEvent;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeEvent;

import java.util.UUID;

import static com.haulmont.cuba.gui.data.CollectionDatasource.Operation.ADD;
import static com.haulmont.cuba.gui.data.CollectionDatasource.Operation.REMOVE;

/**
 * Created by DukeKan on 13.10.2017.
 */
public class CustomerPresenterImpl implements Presenter {
    private CustomerEditScreen screen;
    private CustomerModel model;

    public CustomerPresenterImpl(CustomerEdit screen) {
        this.screen = screen;
        this.model = new CustomerModelImpl();
        init();
    }

    private void init() {
        ListenerBuilder.buildPropertyListener(Customer.class)
                .setDatasource(screen.getCustomerDs())
                .setAfterNonSuccessValidationHandler((event) ->
                        setValueIgnoreListeners(event.getDs(), event.getProperty(), event.getPrevValue()))
                .setValidationHandler(this::validateCustomerPropertyChange)
                .build();

        ListenerBuilder.buildCollectionChangeListener(Order.class)
                .setDatasource(screen.getOrdersDs())
                .setAfterSuccessValidationHandler(this::processCollectionChange)
                .setAfterNonSuccessValidationHandler((event) -> processItemsIgnoreListeners(event.getDs(), event.getItems(), ADD))
                .setValidationHandler(this::validateOrderCollectionChange)
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
                screen.showIncorrectNameNotification();
                throw new ValidationException("Orders amount!");
            }
        }
    }

    private void processCollectionChange(CollectionChangeEvent<Order, UUID> event) {
        if (event.getOperation().equals(REMOVE)) {
            event.getItems().forEach(screen.getOrdersDs()::removeItem);
        }
    }

    public void setModel(CustomerModel model) {
        this.model = model;
    }
}
