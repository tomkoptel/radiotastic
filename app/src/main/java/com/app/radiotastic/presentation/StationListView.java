package com.app.radiotastic.presentation;

import com.app.radiotastic.model.StationModel;

import java.util.Collection;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public interface StationListView {
    void renderStationList(Collection<StationModel> stationModels);
}
