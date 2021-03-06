/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.models.impl;

import com.company.sales.entity.Customer;
import com.company.sales.entity.Order;
import com.company.sales.mvp.models.interfaces.CustomerModel;
import com.company.sales.beans_ext.AppBeansExt;
import com.company.sales.services.CustomerService;

import java.util.Collection;

/**
 * Created by DukeKan on 13.10.2017.
 */
public class CustomerModelImpl implements CustomerModel<Customer> {

    private CustomerService customerService = AppBeansExt.get(CustomerService.NAME);

    @Override
    public boolean allOrdersAreZero(Collection<Order> orders) {
        return orders.stream().allMatch(order -> order.getAmount() != null && order.getAmount().toBigInteger().intValue() == 0);
    }

    @Override
    public int getOrdersAmount(Customer customer) {
        return customerService.countAmount(customer); // такой проброс выглядит некрасиво, но зато одна точка входа в сервис
    }
}
