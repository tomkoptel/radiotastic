/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.data.transform;

import com.udacity.study.jam.radiotastic.StationItem;
import com.udacity.study.jam.radiotastic.db.station.StationCursor;

public class StationCursorTransformer {
    public StationItem transform(StationCursor stationCursor) {
        return new StationItem()
                .withId(stationCursor.getStationId())
                .withBitrate(String.valueOf(stationCursor.getBitrate()))
                .withCountry(stationCursor.getCountry())
                .withName(stationCursor.getName())
                .withWebsite(stationCursor.getWebsite())
                .withStatus(stationCursor.getStatus().ordinal())
                .withStreamurl(stationCursor.getStreamurl());
    }
}
