package com.app.radiotastic.data.cache;

import com.app.radiotastic.data.entity.DirbleStation;

import java.util.Collection;

/**
 * @author Tom Koptel
 */
public interface StationCache {
    rx.Observable<Collection<DirbleStation>> get();
    void put(Collection<DirbleStation> stations);
}
