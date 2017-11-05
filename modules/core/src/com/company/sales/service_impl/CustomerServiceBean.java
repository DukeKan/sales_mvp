/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.service_impl;

import com.company.sales.entity.Customer;
import com.company.sales.entity.Order;
import com.company.sales.services.CustomerService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.haulmont.cuba.core.global.View.LOCAL;

/**
 * Created by DukeKan on 21.10.2017.
 */
@Service(CustomerService.NAME)
public class CustomerServiceBean implements CustomerService {

    @Inject
    private DataManager dataManager;

    @Override
    public int countAmount(Customer customer) {
        LoadContext<Order> lc = LoadContext.create(Order.class);
        lc.setQueryString("select o from sales$Order o where o.customer.id = :customer");
        lc.getQuery().setParameter("customer", customer);
        lc.setView(LOCAL);
        return dataManager.loadList(lc).stream().mapToInt(order -> order.getAmount().intValue()).sum();
    }
}
