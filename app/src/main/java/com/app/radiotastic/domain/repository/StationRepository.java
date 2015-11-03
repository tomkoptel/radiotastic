package com.app.radiotastic.domain.repository;

import com.app.radiotastic.domain.Station;

import java.util.List;

/**
 * @author Tom Koptel
 */
public interface StationRepository {
    rx.Observable<List<Station>> stations();
}
