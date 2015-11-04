package com.app.radiotastic.domain.executor;

import rx.Scheduler;

/**
 * @author Tom Koptel
 */
public interface PostExecutionThread {
    Scheduler getScheduler();
}
