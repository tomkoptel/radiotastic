package com.app.radiotastic.domain.interactor;

import com.app.radiotastic.domain.executor.PostExecutionThread;
import com.app.radiotastic.domain.executor.PreExecutionThread;
import com.app.radiotastic.domain.repository.StationRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public final class GetStationList extends UseCase {
    private final StationRepository mStationRepository;

    @Inject
    public GetStationList(StationRepository repository,
                          PreExecutionThread preExecutionThread,
                          PostExecutionThread postExecutionThread) {
        super(preExecutionThread, postExecutionThread);
        mStationRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return mStationRepository.stations();
    }
}
