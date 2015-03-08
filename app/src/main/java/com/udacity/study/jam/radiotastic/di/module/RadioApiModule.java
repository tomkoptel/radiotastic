/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.di.module;

import android.content.Context;

import com.udacity.study.jam.radiotastic.api.ApiEndpoint;
import com.udacity.study.jam.radiotastic.api.DirbleApiKey;
import com.udacity.study.jam.radiotastic.api.DirbleClient;
import com.udacity.study.jam.radiotastic.api.DirbleRadioApi;
import com.udacity.study.jam.radiotastic.api.RadioApi;
import com.udacity.study.jam.radiotastic.network.AppUrlConnectionClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.UrlConnectionClient;

@Module
final public class RadioApiModule {

    @Singleton
    @Provides
    public RadioApi provideRadioApi(DirbleApiKey apiKey, DirbleClient dirbleClient) {
        return new DirbleRadioApi(apiKey, dirbleClient);
    }

    @Singleton
    @Provides
    DirbleApiKey provideDirbleApiKey(Context context) {
        return new DirbleApiKey(context);
    }

    @Provides
    @Singleton
    UrlConnectionClient provideUrlConnectionClient() {
        return new AppUrlConnectionClient();
    }

    @Provides
    ApiEndpoint provideApiEndpoint(Context context) {
        return new ApiEndpoint(context);
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter(UrlConnectionClient connectionClient,
                                   ApiEndpoint endpoint) {
        return new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setClient(connectionClient)
                .build();
    }

    @Provides
    @Singleton
    DirbleClient dirbleClient(RestAdapter restAdapter) {
        return restAdapter.create(DirbleClient.class);
    }

}
