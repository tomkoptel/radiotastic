package com.app.radiotastic.data.repository.datasource;

import com.app.radiotastic.data.cache.StationCache;
import com.app.radiotastic.data.entity.DirbleStation;
import com.app.radiotastic.data.net.DirbleRestApi;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * @author Tom Koptel
 */
public class CloudStationDataSource implements StationDataStore {

    private final DirbleRestApi mRestApi;
    private final StationCache mStationCache;

    public CloudStationDataSource(StationCache stationCache, DirbleRestApi restApi) {
        mRestApi = restApi;
        mStationCache = stationCache;
    }

    @Override
    public Observable<List<DirbleStation>> listStations() {
        return mRestApi.fetchAllStations().doOnNext(new Action1<List<DirbleStation>>() {
            @Override
            public void call(List<DirbleStation> dirbleStations) {
                mStationCache.put(dirbleStations);
            }
        });
    }
}
