package com.app.radiotastic.data.repository.datasource;

import com.app.radiotastic.data.entity.DirbleStation;

import java.util.List;

/**
 * @author Tom Koptel
 */
public interface StationDataStore {
    rx.Observable<List<DirbleStation>> listStations();
}
