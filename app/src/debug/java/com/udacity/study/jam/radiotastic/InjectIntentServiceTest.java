/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic;

import android.test.ServiceTestCase;

import javax.inject.Inject;

import retrofit.Endpoint;

public class InjectIntentServiceTest extends ServiceTestCase<StationSyncServiceWrapper> {

    @Inject
    public Endpoint apiEndpoint;

    public InjectIntentServiceTest(Class serviceClass) {
        super(serviceClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        App app = App.get(getContext());
        app.setMockMode(true);
        app.graph().inject(this);
    }

    @Override
    protected void tearDown() throws Exception {
        App.get(getContext()).setMockMode(false);
    }

}
