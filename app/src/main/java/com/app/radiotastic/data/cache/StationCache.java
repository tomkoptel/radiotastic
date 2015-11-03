package com.app.radiotastic.data.cache;

import com.app.radiotastic.data.entity.DirbleStation;

import java.util.List;

/**
 * @author Tom Koptel
 */
public interface StationCache {
    rx.Observable<List<DirbleStation>> get();
    void put(List<DirbleStation> stations);
}
