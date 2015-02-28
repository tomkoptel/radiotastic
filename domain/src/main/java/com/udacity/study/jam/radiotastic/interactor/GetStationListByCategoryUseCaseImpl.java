/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.interactor;

import com.udacity.study.jam.radiotastic.Station;
import com.udacity.study.jam.radiotastic.exception.ErrorBundle;
import com.udacity.study.jam.radiotastic.executor.PostExecutionThread;
import com.udacity.study.jam.radiotastic.executor.ThreadExecutor;
import com.udacity.study.jam.radiotastic.repository.StationRepository;

import java.util.Collection;

public class GetStationListByCategoryUseCaseImpl implements GetStationListByCategoryUseCase {
    private final StationRepository stationRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private String categoryId;
    private Callback callback;

    public GetStationListByCategoryUseCaseImpl(StationRepository stationRepository,
                                               ThreadExecutor threadExecutor,
                                               PostExecutionThread postExecutionThread) {
        if (stationRepository == null || threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.stationRepository = stationRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(String categoryId, Callback callback) {
        if (categoryId == null || categoryId.equals("") || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.categoryId = categoryId;
        this.callback = callback;
        threadExecutor.execute(this);
    }

    @Override
    public void run() {
        stationRepository.getStationListByCategory(categoryId, stationListCallback);
    }

    private final StationRepository.StationListCallback stationListCallback =
            new StationRepository.StationListCallback() {
                @Override
                public void onStationListLoaded(Collection<Station> stationsCollection) {
                    notifyGetStationListSuccessfully(stationsCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetStationListSuccessfully(final Collection<Station> stationsCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onStationListLoaded(stationsCollection);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorBundle);
            }
        });
    }

}
