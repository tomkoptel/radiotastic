package com.app.radiotastic.data.cache;

import com.app.radiotastic.data.entity.DirbleStation;

import java.util.Collection;

import rx.Observable;

/**
 * @author Tom Koptel
 */
public class StationCacheImpl implements StationCache {
    @Override
    public Observable<Collection<DirbleStation>> get() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void put(Collection<DirbleStation> stations) {
        throw new UnsupportedOperationException();
    }
}
