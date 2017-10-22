/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales;

import com.company.sales.entity.Customer;
import com.company.sales.entity.Order;
import com.company.sales.gui.customer.CustomerEdit;
import com.company.sales.mvp.models.impl.CustomerModelImpl;
import com.company.sales.mvp.presentes.impl.CustomerPresenterImpl;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.CollectionDatasource.CollectionChangeEvent;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.Datasource.ItemPropertyChangeEvent;
import static org.mockito.Mockito.*;
import mockit.Mocked;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static com.haulmont.cuba.gui.data.CollectionDatasource.Operation.REMOVE;

public class CustomerPresenterTest extends GuiTestCase {

    CustomerEdit editor;

    @Mocked
    Window.Editor frame;

    private Customer customer;
    private Order order;
    private String prevValue;
    private String newValue;
    private CustomerModelImpl customerModel;
    private CustomerPresenterImpl customerPresenter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        editor = new CustomerEdit();
        editor.setWrappedFrame(frame);
        customer = new Customer();
        prevValue = "Prev";
        newValue = "New";
        customer.setName(prevValue);
        order = new Order();
        editor.init(Collections.emptyMap());
        customerModel = mock(CustomerModelImpl.class);

        Datasource<Customer> customerDs = createDatasource(customer, "customerDs", View.LOCAL);
        editor.setCustomerDs(customerDs);

        CollectionDatasource<Order, UUID> ordersDs = createCollectionDatasource(order, "ordersDs", View.LOCAL);
        editor.setOrdersDs(ordersDs);

        customerPresenter = new CustomerPresenterImpl(editor);
        customerPresenter.setModel(customerModel);
    }

    @Test(expected = ValidationException.class)
    public void testPropertyValidate() throws ValidationException {
        ItemPropertyChangeEvent event = new ItemPropertyChangeEvent<Customer>(null, null, "name", prevValue, newValue);
        customerPresenter.validate(event);
    }

    @Test(expected = ValidationException.class)
    public void testCollectionChangeValidate() throws ValidationException {
        CollectionChangeEvent<Order, UUID> event = new CollectionChangeEvent<>(null, REMOVE, new ArrayList<>());
        when(customerModel.allOrdersAreZero(anyCollection())).thenReturn(true);
        customerPresenter.validate(event);
        verify(customerModel).allOrdersAreZero(anyCollection());
    }
}