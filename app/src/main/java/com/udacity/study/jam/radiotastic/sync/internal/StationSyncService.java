/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.sync.internal;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.udacity.study.jam.radiotastic.MainApplication;
import com.udacity.study.jam.radiotastic.api.RadioApi;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataColumns;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataContentValues;

import javax.inject.Inject;

import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class StationSyncService extends IntentService {
    public static final String ACTION = "com.udacity.study.jam.radiotastic.SyncStation";
    public static final String STATION_ID_ARG = "STATION_ID";

    @Inject
    RadioApi radioApi;

    public StationSyncService() {
        super(StationSyncService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.get(this).component().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null && intent.getExtras().containsKey(STATION_ID_ARG)) {
            String stationId = extras.getString(STATION_ID_ARG);
            try {
                Response response = radioApi.getStation(stationId);

                StationMetaDataContentValues metaDataContentValues = new StationMetaDataContentValues();
                metaDataContentValues.putStationId(Long.valueOf(stationId));
                metaDataContentValues.putMeta(response.getBody().toString());
                getContentResolver().insert(StationMetaDataColumns.CONTENT_URI, metaDataContentValues.values());
            } catch (RetrofitError error) {
                Timber.e(error, "Failed to fetch station meta data");
            }
        }
    }
}
