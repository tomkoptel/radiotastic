package com.app.radiotastic.domain.repository;

import com.app.radiotastic.domain.Station;

import java.util.Collection;

/**
 * @author Tom Koptel
 */
public interface StationRepository {
    rx.Observable<Collection<Station>> stations();
}
