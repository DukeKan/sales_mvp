/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales;

import com.company.sales.entity.Customer;
import com.haulmont.chile.core.model.Session;
import com.haulmont.cuba.client.sys.PersistenceManagerClient;
import com.haulmont.cuba.client.testsupport.CubaClientTestCase;
import com.haulmont.cuba.client.testsupport.TestMetadataClient;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.sys.MetadataImpl;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.DsBuilder;
import com.haulmont.cuba.gui.data.impl.DatasourceImpl;
import com.haulmont.cuba.gui.executors.BackgroundWorker;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.entity.User;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * Created by DukeKan on 19.10.2017.
 */
public class GuiTestCase extends CubaClientTestCase {

    @Mocked
    protected ComponentsFactory componentsFactory;

    @Mocked
    protected PersistenceManagerClient persistenceManagerService;
    @Mocked
    protected BackgroundWorker backgroundWorker;
    @Mocked
    protected Session metadataSession;

    @Before
    public void setUp() throws Exception {
        addEntityPackage("com.haulmont.cuba");
        addEntityPackage("com.company.sales.entity");
        //setViewConfig("_local");
        setupInfrastructure();

        metadataSession = metadata.getSession();

        new NonStrictExpectations() {

            {
                AppBeans.get(BackgroundWorker.NAME); result = backgroundWorker;
                AppBeans.get(BackgroundWorker.class); result = backgroundWorker;
                AppBeans.get(BackgroundWorker.NAME, BackgroundWorker.class); result = backgroundWorker;

                AppBeans.get(ComponentsFactory.NAME); result = componentsFactory;
                AppBeans.get(PersistenceManagerClient.NAME); result = persistenceManagerService;
                AppBeans.get(PersistenceManagerClient.class); result = persistenceManagerService;
                AppBeans.get(PersistenceManagerClient.NAME, PersistenceManagerClient.class); result = persistenceManagerService;

                persistenceManagerService.getMaxFetchUI(anyString); result = 10000;
            }
        };
    }

    protected <E extends Entity> Datasource<E> getDatasource(E entity, String datasourceId, String view) {
        // noinspection unchecked
        Datasource<E> datasource = new DsBuilder()
                .setId(datasourceId)
                .setJavaClass(entity.getClass())
                .setView(viewRepository.getView(entity.getMetaClass(), view))
                .buildDatasource();

        datasource.setItem(entity);
        ((DatasourceImpl) datasource).valid();

        return datasource;
    }
}
