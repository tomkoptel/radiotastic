/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.sync.internal;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.udacity.study.jam.radiotastic.App;
import com.udacity.study.jam.radiotastic.api.RadioApi;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataColumns;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataContentValues;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataSelection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.inject.Inject;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedInput;
import timber.log.Timber;

public class StationSyncService extends IntentService {
    public static final String ACTION = "com.udacity.study.jam.radiotastic.SyncStation";
    public static final String STATION_ID_ARG = "STATION_ID";

    @Inject
    RadioApi radioApi;

    public static void performSync(Context context, String stationId) {
        Intent startIntent = new Intent();
        startIntent.setClass(context, StationSyncService.class);
        startIntent.setAction(StationSyncService.ACTION);
        startIntent.putExtra(StationSyncService.STATION_ID_ARG, stationId);
        context.startService(startIntent);
    }

    public StationSyncService() {
        super(StationSyncService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.get(this).graph().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null && intent.getExtras().containsKey(STATION_ID_ARG)) {
            String stationId = extras.getString(STATION_ID_ARG);
            fetchStationMetaData(stationId);
        }
    }

    private void fetchStationMetaData(String stationId) {
        try {
            Response response = radioApi.getStation(stationId);
            if (response.getStatus() == 200) {
                TypedInput input = response.getBody();
                try {
                    InputStream inputStream = input.in();
                    String json = convertToString(inputStream);
                    closeQuietly(inputStream);
                    persistResponse(stationId, json);
                } catch (IOException e) {
                    saveResponse(stationId, null);
                    Timber.e(e, "Failed to close input stream");
                }
            }
        } catch (RetrofitError error) {
            saveResponse(stationId, null);
            Timber.e(error, "Failed to fetch station meta data");
        }
    }

    private void persistResponse(String stationId, String response) {
        StationMetaDataSelection selection = new StationMetaDataSelection();
        selection.stationId(Long.valueOf(stationId));
        getContentResolver().delete(StationMetaDataColumns.CONTENT_URI, selection.sel(), selection.args());

        saveResponse(stationId, response);
    }

    private void saveResponse(String stationId, String response) {
        StationMetaDataContentValues metaDataContentValues =
                new StationMetaDataContentValues()
                        .putStationId(Long.valueOf(stationId))
                        .putMeta(response)
                        .putCreatedAt(new Date());
        getContentResolver().insert(StationMetaDataColumns.CONTENT_URI, metaDataContentValues.values());
    }

    @NonNull
    private String convertToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        closeQuietly(inputStream);
        return stringBuilder.toString();
    }

    private void closeQuietly(InputStream input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ioe) {
            // Ignore exception due to quite close
        }
    }
}
