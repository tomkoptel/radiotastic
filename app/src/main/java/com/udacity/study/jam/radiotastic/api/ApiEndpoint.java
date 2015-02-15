/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.api;

import android.content.Context;

import com.udacity.study.jam.radiotastic.R;

import retrofit.Endpoint;

public class ApiEndpoint implements Endpoint {
    private final Context mContext;

    public ApiEndpoint(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String getUrl() {
        return mContext.getString(R.string.api_endpoint);
    }

    @Override
    public String getName() {
        return "Represents current version of Dirble API";
    }
}
