/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.gui.customer;

import com.company.sales.entity.Customer;
import com.company.sales.entity.Order;
import com.company.sales.gui.Screen;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by DukeKan on 13.10.2017.
 */
public interface CustomerEditScreen extends Screen<Customer> {

    void addCalculateBtnListener(Consumer<Customer> listener);

    Datasource<Customer> getCustomerDs();
    CollectionDatasource<Order, UUID> getOrdersDs();

    void showIncorrectNameNotification();
    void showIncorrectOrderAmountNotification();
    void showAmountSum(int amount);
}
