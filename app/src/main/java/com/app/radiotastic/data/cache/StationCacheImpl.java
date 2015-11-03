package com.app.radiotastic.data.cache;

import com.app.radiotastic.data.db.station.StationColumns;
import com.app.radiotastic.data.entity.DirbleStation;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * @author Tom Koptel
 */
@Singleton
public class StationCacheImpl implements StationCache {

    private final StorIOSQLite mStorIOSQLite;

    @Inject
    public StationCacheImpl(StorIOSQLite storIOSQLite) {
        mStorIOSQLite = storIOSQLite;
    }

    @Override
    public Observable<List<DirbleStation>> get() {
        return mStorIOSQLite.get()
                .listOfObjects(DirbleStation.class)
                .withQuery(Query.builder().table(StationColumns.TABLE_NAME).build())
                .prepare()
                .createObservable();
    }

    @Override
    public void put(List<DirbleStation> stations) {
        mStorIOSQLite.put().objects(stations).prepare().executeAsBlocking();
    }

    @Override
    public boolean hasCache() {
        Integer count = mStorIOSQLite.get()
                .numberOfResults()
                .withQuery(Query.builder().table(StationColumns.TABLE_NAME).build())
                .prepare()
                .executeAsBlocking();
        return count > 0;
    }
}
