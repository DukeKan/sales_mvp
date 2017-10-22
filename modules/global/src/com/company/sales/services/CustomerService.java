/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.services;

import com.company.sales.entity.Customer;

/**
 * Created by DukeKan on 21.10.2017.
 */
public interface CustomerService {
    String NAME = "sales_CustomerService";

    int countAmount(Customer customer);
}
