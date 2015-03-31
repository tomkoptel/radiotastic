/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.test;

import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.udacity.study.jam.radiotastic.InjectIntentServiceTest;
import com.udacity.study.jam.radiotastic.StationSyncServiceWrapper;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataColumns;
import com.udacity.study.jam.radiotastic.sync.internal.StationSyncService;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class StationSyncServiceTest extends InjectIntentServiceTest {

    private MockWebServer server;
    private CountDownLatch latch;

    public StationSyncServiceTest() {
        super(StationSyncServiceWrapper.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getContext().getContentResolver().delete(StationMetaDataColumns.CONTENT_URI, null, null);

        server = new MockWebServer();
        try {
            server.play();
        } catch (IOException e) {
            Log.i(StationSyncServiceTest.class.getSimpleName(), "Failed to start MockWebServer");
            throw new RuntimeException(e);
        }

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{}"));

        when(apiEndpoint.getUrl()).thenReturn("http://localhost:" + server.getPort());
    }

    @Override
    protected void setupService() {
        super.setupService();

        latch = new CountDownLatch(1);
        getService().setLatch(latch);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        try {
            if (server != null) {
                server.shutdown();
            }
        } catch (IOException e) {
            Log.i(StationSyncServiceTest.class.getSimpleName(), "Failed to shutdown MockWebServer");
        }

    }

    public void testAddAccount() throws InterruptedException {
        Intent startIntent = new Intent();
        startIntent.setClass(getContext(), StationSyncService.class);
        startIntent.setAction(StationSyncService.ACTION);
        startIntent.putExtra(StationSyncService.STATION_ID_ARG, "1");
        startService(startIntent);

        latch.await();

        Cursor cursor = mContext.getContentResolver()
                .query(StationMetaDataColumns.CONTENT_URI,
                new String[]{"_id"}, null, null, null);
        assertThat(cursor.getCount(), is(1));
    }

}
