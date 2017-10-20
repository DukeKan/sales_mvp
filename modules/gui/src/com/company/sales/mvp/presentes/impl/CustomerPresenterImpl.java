/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.presentes.impl;


import com.company.sales.entity.Customer;
import com.company.sales.gui.PropertyChangedEvent;
import com.company.sales.gui.customer.CustomerEditScreen;
import com.company.sales.mvp.models.impl.CustomerModelImpl;
import com.company.sales.mvp.models.interfaces.CustomerModel;
import com.company.sales.mvp.presentes.interfaces.Presenter;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.data.Datasource;
import com.company.sales.gui.customer.CustomerEdit;
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

    // сделать лямбдой метод валидации
    private void init() {
        registerPropertyValidation(screen.getCustomerDs());
    }

    @Override
    public void validate(PropertyChangedEvent event) throws ValidationException{
        if (event.getPropertyName().equals("name") && !"Test".equals(event.getNewValue())) {
            screen.showIncorrectNameNotification();
            throw new ValidationException("Incorrect data");
        }
    }

    @Override
    public void setValue(String propertyName, Object value) {
        Datasource<Customer> customerDs = screen.getCustomerDs();
        disableListeners(customerDs);
        screen.setPropertyValue(customerDs, propertyName, value);
        enableListeners(customerDs);
    }
}
