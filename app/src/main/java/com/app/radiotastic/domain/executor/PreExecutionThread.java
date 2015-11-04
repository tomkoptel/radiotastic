package com.app.radiotastic.domain.executor;

import rx.Scheduler;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public interface PreExecutionThread {
    Scheduler getScheduler();
}
