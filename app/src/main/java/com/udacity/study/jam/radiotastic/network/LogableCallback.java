/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.network;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;


public abstract class LogableCallback<T> implements Callback<T> {
    private static final String LOG_TAG = LogableCallback.class.getSimpleName();

    public LogableCallback() {
        Timber.tag(LOG_TAG);
    }

    public abstract void semanticSuccess(T t, Response response);

    public abstract void semanticFailure(RetrofitError error);

    @Override
    public void success(T t, Response response) {
        Timber.i("Network call success! Response: " + response);
        semanticSuccess(t, response);
    }

    @Override
    public void failure(RetrofitError error) {
        Timber.e(error, "Network call crashed");
        semanticFailure(error);
    }
}
