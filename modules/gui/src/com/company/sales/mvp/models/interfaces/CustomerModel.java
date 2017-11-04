/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.models.interfaces;


import com.company.sales.entity.Customer;
import com.company.sales.entity.Order;
import com.company.sales.mvp.models.Model;

import java.util.Collection;

/**
 * Created by DukeKan on 13.10.2017.
 */
public interface CustomerModel<E extends Customer> extends Model{

    boolean allOrdersAreZero(Collection<Order> orders);
    int getOrdersAmount(Customer customer);
}
