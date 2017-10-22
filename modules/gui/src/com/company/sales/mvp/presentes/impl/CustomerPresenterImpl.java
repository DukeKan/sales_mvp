/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.presentes.impl;


import com.company.sales.entity.Customer;
import com.company.sales.entity.Order;
import com.company.sales.gui.customer.CustomerEditScreen;
import com.company.sales.mvp.models.impl.CustomerModelImpl;
import com.company.sales.mvp.models.interfaces.CustomerModel;
import com.company.sales.mvp.presentes.interfaces.Presenter;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.CollectionDatasource.CollectionChangeEvent;
import com.haulmont.cuba.gui.data.Datasource;
import com.company.sales.gui.customer.CustomerEdit;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeEvent;

import java.util.UUID;

/**
 * Created by DukeKan on 13.10.2017.
 */
public class CustomerPresenterImpl implements Presenter{
    private CustomerEditScreen screen;
    private CustomerModel model;

    public CustomerPresenterImpl(CustomerEdit screen) {
        this.screen = screen;
        this.model = new CustomerModelImpl();
        init();
    }

    private void init() {
        registerPropertyChangeListener(
                screen.getCustomerDs(),
                this::validate,
                (event) -> {},
                (event) -> setValue(event.getProperty(), event.getPrevValue()));
        registerCollectionChangeListener(
                screen.getOrdersDs(),
                this::validate,
                this::processCollectionChange,
                this::canselOrdersCollectionChange);
        screen.addCalculateBtnListener(this::onCalculatePressed);
    }

    private void onCalculatePressed(Customer customer) {
        int ordersAmount = model.getOrdersAmount(customer);
        screen.showAmountSum(ordersAmount);
    }

    private void canselOrdersCollectionChange(CollectionChangeEvent<Order, UUID> event) {
        disableCollectionChangeListeners(event.getDs());
        event.getItems().forEach(screen.getOrdersDs()::addItem);
        enableCollectionChangeListeners(event.getDs());
    }

    public void validate(ItemPropertyChangeEvent<Customer> event) throws ValidationException{
        if (event.getProperty().equals("name") && !"Test".equals(event.getValue())) {
            screen.showIncorrectNameNotification();
            throw new ValidationException("Incorrect data");
        }
    }

    public void validate(CollectionChangeEvent<Order, UUID> event) throws ValidationException{
        if (event.getOperation().equals(CollectionDatasource.Operation.REMOVE)) {
            if (model.allOrdersAreZero(event.getItems())) {
                screen.showIncorrectNameNotification();
                throw new ValidationException("Orders amount!");
            }
        }
    }

    private void processCollectionChange(CollectionChangeEvent<Order, UUID> event){
        if (event.getOperation().equals(CollectionDatasource.Operation.REMOVE)) {
            event.getItems().forEach(screen.getOrdersDs()::removeItem);
        }
    }

    private void setValue(String propertyName, Object value) {
        Datasource<Customer> customerDs = screen.getCustomerDs();
        disablePropertyChangeListeners(customerDs);
        screen.setPropertyValue(customerDs, propertyName, value);
        enablePropertyChangeListeners(customerDs);
    }
}
