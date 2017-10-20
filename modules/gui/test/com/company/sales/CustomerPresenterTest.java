/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales;

import com.company.sales.entity.Customer;
import com.company.sales.gui.customer.CustomerEdit;
import com.company.sales.mvp.presentes.impl.CustomerPresenterImpl;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeEvent;
import mockit.Mocked;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class CustomerPresenterTest extends GuiTestCase {

    CustomerEdit editor;

    @Mocked
    Window.Editor frame;

    private Customer customer;
    private String prevValue;
    private String newValue;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        editor = new CustomerEdit();
        editor.setWrappedFrame(frame);
        customer = new Customer();
        prevValue = "Prev";
        newValue = "New";
        customer.setName(prevValue);
    }

    @Test(expected = ValidationException.class)
    public void testValidate() throws ValidationException {
        editor.init(Collections.emptyMap());

        Datasource<Customer> ds = createDatasource(customer, "customerDs", View.LOCAL);
        editor.setCustomerDs(ds);

        ItemPropertyChangeEvent event = new ItemPropertyChangeEvent<Customer>(null, null, "name", prevValue, newValue);
        CustomerPresenterImpl customerPresenter = new CustomerPresenterImpl(editor);
        customerPresenter.validate(event);
    }
}