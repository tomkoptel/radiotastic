package com.app.radiotastic.data.repository;

import com.app.radiotastic.data.entity.DirbleStation;
import com.app.radiotastic.data.entity.mapper.StationDataMapper;
import com.app.radiotastic.data.repository.datasource.StationDataStore;
import com.app.radiotastic.data.repository.datasource.StationDataStoreFactory;
import com.app.radiotastic.domain.Station;
import com.app.radiotastic.domain.repository.StationRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author Tom Koptel
 */
@Singleton
public class StationDataRepository implements StationRepository {

    private final StationDataMapper mStationDataMapper;
    private final StationDataStoreFactory mDataStoreFactory;

    @Inject
    public StationDataRepository(StationDataMapper stationDataMapper, StationDataStoreFactory dataStoreFactory) {
        mStationDataMapper = stationDataMapper;
        mDataStoreFactory = dataStoreFactory;
    }

    @Override
    public Observable<List<Station>> stations() {
        StationDataStore dataSource = mDataStoreFactory.create();
        return dataSource.listStations().map(new Func1<List<DirbleStation>, List<Station>>() {
            @Override
            public List<Station> call(List<DirbleStation> dirbleStations) {
                return mStationDataMapper.transform(dirbleStations);
            }
        });
    }
}
