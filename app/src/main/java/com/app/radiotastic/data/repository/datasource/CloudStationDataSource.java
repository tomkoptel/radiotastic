package com.app.radiotastic.data.repository.datasource;

import com.app.radiotastic.data.entity.DirbleStation;
import com.app.radiotastic.data.net.DirbleRestApi;

import java.util.List;

import rx.Observable;

/**
 * @author Tom Koptel
 */
public class CloudStationDataSource implements StationDataStore {

    private final DirbleRestApi mRestApi;

    public CloudStationDataSource(DirbleRestApi restApi) {
        mRestApi = restApi;
    }

    @Override
    public Observable<List<DirbleStation>> listStations() {
        return mRestApi.fetchAllStations();
    }
}
