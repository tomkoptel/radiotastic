package com.app.radiotastic.presentation.mappers;

import com.app.radiotastic.domain.Station;
import com.app.radiotastic.internal.di.PerActivity;
import com.app.radiotastic.presentation.model.StationModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Tom Koptel
 */
@PerActivity
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
            stationModel = StationModel.builder()
                    .setId(station.getId())
                    .setName(station.getName())
                    .create();
        }
        return stationModel;
    }
}
