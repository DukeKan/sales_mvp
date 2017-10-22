/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.models.interfaces;


import com.company.sales.entity.Customer;
import com.company.sales.entity.Order;

import java.util.List;

/**
 * Created by DukeKan on 13.10.2017.
 */
public interface CustomerModel<E extends Customer> {

    boolean allOrdersAreZero(List<Order> orders);
    int getOrdersAmount(Customer customer);
}
