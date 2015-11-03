package com.app.radiotastic.data.repository.datasource;

import com.app.radiotastic.data.cache.StationCache;
import com.app.radiotastic.data.net.DirbleRestApi;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Tom Koptel
 */
@Singleton
public class StationDataStoreFactory {

    private final StationCache mStationCache;
    private final DirbleRestApi mRestApi;

    @Inject
    public StationDataStoreFactory(StationCache stationCache, DirbleRestApi restApi) {
        mStationCache = stationCache;
        mRestApi = restApi;
    }

    public StationDataStore create() {
        if (mStationCache.hasCache()) {
            return new DatabaseStationDataSource(mStationCache);
        } else {
            return new CloudStationDataSource(mRestApi);
        }
    }
}
