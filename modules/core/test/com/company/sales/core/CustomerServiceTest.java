/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.core;

import com.company.sales.entity.Customer;
import com.company.sales.services.CustomerService;
import com.haulmont.cuba.core.global.*;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Sample integration test.
 */
public class CustomerServiceTest {

    /**
     * Single-instance test container
     */
    @ClassRule
    public static SalesTestContainer cont = SalesTestContainer.Common.INSTANCE;

    private Metadata metadata;

    @Before
    public void setUp() throws Exception {
        metadata = cont.metadata();
    }

    @Test
    public void testCreateCustomer() throws Exception {
        // Get a managed bean (or service) from container
        DataManager dataManager = AppBeans.get(DataManager.class);

        // Create new Customer
        Customer customer = metadata.create(Customer.class);
        customer.setName("Test customer");

        // Save the customer to the database
        dataManager.commit(customer);

        // Load the customer by ID
        Customer loaded = dataManager.load(
                LoadContext.create(Customer.class).setId(customer.getId()).setView(View.LOCAL));

        assertNotNull(loaded);
        assertEquals(customer.getName(), loaded.getName());

        // Remove the customer
        dataManager.remove(loaded);
    }

    @Test
    public void testCalculateAmount() throws Exception {
        DataManager dataManager = AppBeans.get(DataManager.class);
        CustomerService customerService = AppBeans.get(CustomerService.class);

        Customer customer = metadata.create(Customer.class);
        customer.setName("Test customer");

        customer = dataManager.commit(customer);

        int amount = customerService.countAmount(customer);

        assertEquals(amount, 0);

        // Remove the customer
        dataManager.remove(customer);
    }
}
