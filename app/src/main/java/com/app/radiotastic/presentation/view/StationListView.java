package com.app.radiotastic.presentation.view;

import com.app.radiotastic.presentation.model.StationModel;

import java.util.Collection;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public interface StationListView {
    void renderStationList(Collection<StationModel> stationModels);
}
