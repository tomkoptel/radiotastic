/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.di.module;

import android.content.Context;
import android.os.Bundle;

import com.udacity.study.jam.radiotastic.api.ApiEndpoint;
import com.udacity.study.jam.radiotastic.api.DirbleApiKey;
import com.udacity.study.jam.radiotastic.api.DirbleClient;
import com.udacity.study.jam.radiotastic.api.DirbleRadioApi;
import com.udacity.study.jam.radiotastic.api.FileRadioApi;
import com.udacity.study.jam.radiotastic.api.RadioApi;
import com.udacity.study.jam.radiotastic.network.AppUrlConnectionClient;
import com.udacity.study.jam.radiotastic.sync.SyncHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.UrlConnectionClient;

@Module
final public class RadioApiModule {

    private final Bundle extras;

    public RadioApiModule(Bundle extras) {
        this.extras = extras;
    }

    @Singleton
    @Provides
    public RadioApi provideRadioApi(Context context, DirbleApiKey apiKey, DirbleClient dirbleClient) {
        RadioApi remoteClient = new DirbleRadioApi(apiKey, dirbleClient);
        if (!extras.containsKey(SyncHelper.SYNC_EXTRAS_FLAG)) {
            return remoteClient;
        } else {
            int syncFlag = extras.getInt(SyncHelper.SYNC_EXTRAS_FLAG);
            switch (syncFlag) {
                case SyncHelper.FLAG_SYNC_REMOTE:
                    return remoteClient;
                case SyncHelper.FLAG_SYNC_CACHED:
                    return new FileRadioApi(context);
                default:
                    throw new UnsupportedOperationException("It looks like SYNC_EXTRAS_FLAG was misused");
            }
        }
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
