package com.app.radiotastic.data.entity.mapper;

import com.app.radiotastic.data.entity.DirbleStation;
import com.app.radiotastic.domain.Station;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Tom Koptel
 */
@Singleton
public class StationDataMapper {

    @Inject
    public StationDataMapper() {}

    public List<Station> transform(Collection<DirbleStation> stations) {
        List<Station> stationList = new ArrayList<>(stations.size());
        Station station;
        for (DirbleStation dirbleStation : stations) {
            station = transform(dirbleStation);
            if (station != null) {
                stationList.add(station);
            }
        }
        return stationList;
    }

    public Station transform(DirbleStation dirbleStation) {
        Station station = null;
        if (dirbleStation != null) {
            station = Station.builder()
                    .setId(dirbleStation.getRemoteId())
                    .setName(dirbleStation.getName())
                    .create();
        }
        return station;
    }
}
