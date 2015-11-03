package com.app.radiotastic.data.net;

import com.app.radiotastic.data.entity.DirbleStation;

import java.util.Collection;

import retrofit.http.GET;

/**
 * @author Tom Koptel
 */
public interface DirbleRestApi {
    @GET("stations")
    rx.Observable<Collection<DirbleStation>> fetchAllStations();
}
