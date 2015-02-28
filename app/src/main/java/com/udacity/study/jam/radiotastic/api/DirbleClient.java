/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.api;

import com.udacity.study.jam.radiotastic.pojo.CategoryItem;
import com.udacity.study.jam.radiotastic.pojo.StationData;
import com.udacity.study.jam.radiotastic.pojo.StationItem;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface DirbleClient {
    @GET("/primaryCategories/apikey/{api-key}")
    void listPrimaryCategories(@Path("api-key") String apiKey,
                               Callback<List<CategoryItem>> callback);

    @GET("/stations/apikey/{api-key}/id/{category_id}")
    void listStations(@Path("api-key") String apiKey, @Path("category_id") int categoryID,
                      Callback<List<StationItem>> callback);

    @GET("/station/apikey/{api-key}/id/{station_id}")
    void getStationData(@Path("api-key") String apiKey, @Path("station_id") int stationID,
                      Callback<StationData> callback);

}
