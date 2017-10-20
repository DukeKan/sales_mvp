/*
 * Copyright (c) 2016 Haulmont
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.company.sales.gui.customer;



import com.company.sales.entity.Customer;
import com.company.sales.mvp.presentes.impl.CustomerPresenterImpl;
import com.company.sales.mvp.presentes.interfaces.Presenter;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;

import javax.inject.Inject;

public class CustomerEdit extends AbstractEditor<Customer> implements CustomerEditScreen {
    private Presenter presenter;

    @Inject
    private Datasource<Customer> customerDs;
    @Inject
    private CollectionDatasource ordersDs;

    @Override
    protected void postInit() {
        super.postInit();
        presenter = new CustomerPresenterImpl(this);
    }

    public void showIncorrectNameNotification(){
        showNotification("Incorrect name!");
    }

    public Datasource<Customer> getCustomerDs() {
        return customerDs;
    }

    public void setCustomerDs(Datasource<Customer> customerDs) {
        this.customerDs = customerDs;
    }
}