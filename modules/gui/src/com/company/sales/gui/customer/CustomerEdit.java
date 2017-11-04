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
import com.company.sales.entity.Order;
import com.company.sales.mvp.linkers.MvpLinker;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;

import javax.inject.Inject;
import java.util.UUID;
import java.util.function.Consumer;

import static com.haulmont.cuba.gui.data.CollectionDatasource.Operation.REMOVE;

public class CustomerEdit extends AbstractEditor<Customer> implements CustomerEditScreen {
    private Consumer<Customer> calculateBtnListener;

    @Inject
    private Datasource<Customer> customerDs;
    @Inject
    private CollectionDatasource<Order, UUID> ordersDs;

    @Override
    protected void postInit() {
        super.postInit();
        MvpLinker.create(this);
    }

    public void onRemoveAll() {
        processItems(ordersDs, ordersDs.getItems(), REMOVE);
    }

    public void onCalculate(){
        if (calculateBtnListener != null) {
            calculateBtnListener.accept(getItem());
        }
    }

    public void showIncorrectNameNotification(){
        showNotification("Incorrect name!");
    }

    @Override
    public void showIncorrectOrderAmountNotification() {
        showNotification("Incorrect amount!");
    }

    @Override
    public void showAmountSum(int amount) {
        showNotification("Amount: " + amount);
    }

    @Override
    public void addCalculateBtnListener(Consumer<Customer> listener) {
        calculateBtnListener = listener;
    }

    public Datasource<Customer> getCustomerDs() {
        return customerDs;
    }

    @Override
    public CollectionDatasource<Order, UUID> getOrdersDs() {
        return ordersDs;
    }

    public void setCustomerDs(Datasource<Customer> customerDs) {
        this.customerDs = customerDs;
    }

    public void setOrdersDs(CollectionDatasource<Order, UUID> ordersDs) {
        this.ordersDs = ordersDs;
    }

}