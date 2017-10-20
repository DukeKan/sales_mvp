/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.presentes.impl;


import com.company.sales.entity.Customer;
import com.company.sales.gui.customer.CustomerEditScreen;
import com.company.sales.mvp.models.impl.CustomerModelImpl;
import com.company.sales.mvp.models.interfaces.CustomerModel;
import com.company.sales.mvp.presentes.interfaces.Presenter;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.Datasource;
import com.company.sales.gui.customer.CustomerEdit;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeEvent;

/**
 * Created by DukeKan on 13.10.2017.
 */
public class CustomerPresenterImpl implements Presenter<Customer> {
    private CustomerEditScreen screen;
    private CustomerModel model;

    public CustomerPresenterImpl(CustomerEdit screen) {
        this.screen = screen;
        this.model = new CustomerModelImpl();
        init();
    }

    private void init() {
        registerPropertyValidation(
                screen.getCustomerDs(),
                this::validate,
                (event) -> setValue(event.getProperty(), event.getPrevValue()));
    }

    public void validate(ItemPropertyChangeEvent<Customer> event) throws ValidationException{
        if (event.getProperty().equals("name") && !"Test".equals(event.getValue())) {
            screen.showIncorrectNameNotification();
            throw new ValidationException("Incorrect data");
        }
    }

    private void setValue(String propertyName, Object value) {
        Datasource<Customer> customerDs = screen.getCustomerDs();
        disablePropertyChangeListeners(customerDs);
        screen.setPropertyValue(customerDs, propertyName, value);
        enablePropertyChangeListeners(customerDs);
    }
}
