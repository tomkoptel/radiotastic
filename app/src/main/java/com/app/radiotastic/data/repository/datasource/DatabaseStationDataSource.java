package com.app.radiotastic.data.repository.datasource;

import com.app.radiotastic.data.cache.StationCache;
import com.app.radiotastic.data.entity.DirbleStation;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Tom Koptel
 */
public class DatabaseStationDataSource implements StationDataStore {
    private final StationCache mStationCache;

    @Inject
    public DatabaseStationDataSource(StationCache stationCache) {
        mStationCache = stationCache;
    }

    @Override
    public Observable<List<DirbleStation>> listStations() {
        return mStationCache.get();
    }
}
