package com.app.radiotastic.data.net;

import com.app.radiotastic.data.entity.DirbleStation;

import java.util.List;

import retrofit.http.GET;

/**
 * @author Tom Koptel
 */
public interface DirbleRestApi {
    @GET("stations")
    rx.Observable<List<DirbleStation>> fetchAllStations();
}
