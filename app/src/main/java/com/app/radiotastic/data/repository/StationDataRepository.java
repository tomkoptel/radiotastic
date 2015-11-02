package com.app.radiotastic.data.repository;

import com.app.radiotastic.domain.Station;
import com.app.radiotastic.domain.repository.StationRepository;

import java.util.Collection;

import rx.Observable;

/**
 * @author Tom Koptel
 */
public class StationDataRepository implements StationRepository {
    @Override
    public Observable<Collection<Station>> stations() {
        throw new UnsupportedOperationException();
    }
}
