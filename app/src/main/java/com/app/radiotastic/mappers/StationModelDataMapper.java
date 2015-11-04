package com.app.radiotastic.mappers;

import com.app.radiotastic.domain.Station;
import com.app.radiotastic.model.StationModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Tom Koptel
 */
@Singleton
public final class StationModelDataMapper {
    @Inject
    public StationModelDataMapper() {}

    public List<StationModel> transform(Collection<Station> stations) {
        List<StationModel> stationList = new ArrayList<>(stations.size());
        StationModel stationModel;
        for (Station station : stations) {
            stationModel = transform(station);
            if (stationModel != null) {
                stationList.add(stationModel);
            }
        }
        return stationList;
    }

    public StationModel transform(Station station) {
        StationModel stationModel = null;
        if (station != null) {
            stationModel = new StationModel(station.getId());
            stationModel.setName(station.getName());
        }
        return stationModel;
    }
}
