/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.core;

import com.haulmont.cuba.testsupport.TestContainer;

import java.util.Arrays;
import java.util.Collections;

/**
 *  Container for integration testing.
 */
public class SalesTestContainer extends TestContainer {

    public SalesTestContainer() {
        super();
        appComponents = Collections.singletonList("com.haulmont.cuba");
        appPropertiesFiles = Arrays.asList(
                // List the files defined in your web.xml
                // in appPropertiesConfig context parameter of the core module
                "cuba-app.properties",
                "app.properties",
                // Add this file which is located in CUBA and defines some properties
                // specifically for test environment. You can replace it with your own
                // or add another one in the end.
                "test-app.properties");
        dbDriver = "org.hsqldb.jdbc.JDBCDriver";
        dbUrl = "jdbc:hsqldb:hsql://localhost:9002/sales_test";
        dbUser = "sa";
        dbPassword = "";
    }

    public static class Common extends SalesTestContainer {

        public static final SalesTestContainer.Common INSTANCE = new SalesTestContainer.Common();

        private static volatile boolean initialized;

        private Common() {
        }

        @Override
        public void before() throws Throwable {
            if (!initialized) {
                super.before();
                initialized = true;
            }
            setupContext();
        }

        @Override
        public void after() {
            cleanupContext();
            // never stops - do not call super
        }
    }
}