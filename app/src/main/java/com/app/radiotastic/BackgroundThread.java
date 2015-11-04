package com.app.radiotastic;

import com.app.radiotastic.domain.executor.PreExecutionThread;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * @author Tom Koptel
 */
@Singleton
public final class BackgroundThread implements PreExecutionThread {
    @Inject
    public BackgroundThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return Schedulers.io();
    }
}
