/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.services;

import com.company.sales.entity.Customer;
import com.company.sales.beans_ext.View;

import static com.haulmont.cuba.core.global.View.LOCAL;

/**
 * Created by DukeKan on 21.10.2017.
 */
public interface CustomerService {
    String NAME = "sales_CustomerService";

    int countAmount(@View(name = LOCAL) Customer customer);
}
