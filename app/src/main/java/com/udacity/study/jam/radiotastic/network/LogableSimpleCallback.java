/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.network;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class LogableSimpleCallback<T> extends LogableCallback<T> {
    @Override
    public void semanticSuccess(T t, Response response) {

    }

    @Override
    public void semanticFailure(RetrofitError error) {

    }
}
