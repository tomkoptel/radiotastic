package com.app.radiotastic.data.repository.datasource;

import android.content.Context;

import com.app.radiotastic.R;
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
    private final Context mContext;

    @Inject
    public StationDataStoreFactory(Context context, StationCache stationCache) {
        mStationCache = stationCache;
        mContext = context;
    }

    public StationDataStore create() {
        if (mStationCache.hasCache()) {
            return new DatabaseStationDataSource(mStationCache);
        } else {
            String token = mContext.getString(R.string.dirble_api_key);
            DirbleRestApi restClient = DirbleRestApi.Factory.create(token);
            return new CloudStationDataSource(mStationCache, restClient);
        }
    }
}
