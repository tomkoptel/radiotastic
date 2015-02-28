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

public class GetStationDetailsUseCaseImpl implements GetStationDetailsUseCase{
    private final StationRepository stationRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private String stationId;
    private Callback callback;

    public GetStationDetailsUseCaseImpl(StationRepository stationRepository,
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
    public void execute(String stationId, Callback callback) {
        if (stationId == null || stationId.equals("") || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.stationId = stationId;
        this.callback = callback;
        threadExecutor.execute(this);
    }

    @Override
    public void run() {
        stationRepository.getStationById(stationId, stationDetailsCallback);
    }

    private final StationRepository.StationDetailsCallback stationDetailsCallback =
            new StationRepository.StationDetailsCallback() {
                @Override
                public void onStationLoaded(Station station) {
                    notifyGetStationDetailsSuccessfully(station);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetStationDetailsSuccessfully(final Station user) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onStationDataLoaded(user);
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
