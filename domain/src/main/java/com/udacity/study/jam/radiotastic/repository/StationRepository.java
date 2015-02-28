/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.repository;

import com.udacity.study.jam.radiotastic.Station;
import com.udacity.study.jam.radiotastic.exception.ErrorBundle;

import java.util.Collection;

public interface StationRepository {

    interface StationListCallback {
        void onStationListLoaded(Collection<Station> stationsCollection);
        void onError(ErrorBundle errorBundle);
    }

    interface StationDetailsCallback {
        void onStationLoaded(Station station);
        void onError(ErrorBundle errorBundle);
    }

    void getStationListByCategory(final String categoryId, StationListCallback stationListCallback);
    void getStationById(final String stationId, StationDetailsCallback stationDetailsCallback);
}
