/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.gui.customer;

import com.company.sales.entity.Customer;
import com.company.sales.gui.Screen;
import com.haulmont.cuba.gui.data.Datasource;

/**
 * Created by DukeKan on 13.10.2017.
 */
public interface CustomerEditScreen extends Screen<Customer> {
    Datasource<Customer> getCustomerDs();

    void showIncorrectNameNotification();
}
