package com.app.radiotastic.domain.interactor;

import com.app.radiotastic.domain.executor.PostExecutionThread;
import com.app.radiotastic.domain.executor.PreExecutionThread;

import rx.Subscription;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public abstract class UseCase {

    private final PreExecutionThread mPreExecutionThread;
    private final PostExecutionThread mPostExecutionThread;
    private Subscription mSubscription;

    protected UseCase(PreExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        mPreExecutionThread = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    protected abstract rx.Observable buildUseCaseObservable();

    @SuppressWarnings("unchecked")
    public void execute(rx.Subscriber useCaseSubscriber) {
        mSubscription = buildUseCaseObservable()
                .subscribeOn(mPreExecutionThread.getScheduler())
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribe(useCaseSubscriber);
    }

    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
