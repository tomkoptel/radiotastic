/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.study.jam.radiotastic.CategoryItem;
import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.StationItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collection;

import retrofit.client.Response;
import timber.log.Timber;

public class FileRadioApi implements RadioApi {
    private final Context context;

    public FileRadioApi(Context context) {
        this.context = context;
    }

    @Override
    public Collection<CategoryItem> listPrimaryCategories() {
        InputStream inputStream = context.getResources().openRawResource(R.raw.categories);
        try {
            String json = toString(inputStream);
            Gson gson = new Gson();
            Type type = new TypeToken<Collection<CategoryItem>>() {
            }.getType();
            return gson.fromJson(json, type);
        } catch (IOException e) {
            Timber.e(e, "Failed to open raw resource");
            return null;
        }
    }

    @Override
    public Collection<StationItem> listStations(String categoryId) {
        throw new UnsupportedOperationException("Stations are not cached as file");
    }

    @Override
    public Response getStation(String stationId) {
        throw new UnsupportedOperationException("Station are not cached as file");
    }

    private String toString(InputStream inputStream) throws IOException {
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
