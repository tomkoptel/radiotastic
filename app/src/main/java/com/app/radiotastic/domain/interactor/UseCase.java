package com.app.radiotastic.domain.interactor;

import com.app.radiotastic.domain.executor.PostExecutionThread;
import com.app.radiotastic.domain.executor.ThreadExecutor;

import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public abstract class UseCase {

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;
    private Subscription mSubscription;

    protected UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    protected abstract rx.Observable buildUseCaseObservable();

    @SuppressWarnings("unchecked")
    public void execute(rx.Subscriber useCaseSubscriber) {
        mSubscription = buildUseCaseObservable()
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribe(useCaseSubscriber);
    }

    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
