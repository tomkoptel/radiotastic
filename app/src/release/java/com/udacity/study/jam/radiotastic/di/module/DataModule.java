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

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;

@Module
public class DataModule {

    public DataModule(Bundle extras) {
        this.extras = extras;
    }

    @Provides
    Bundle provideSyncExtras() {
        return extras;
    }

    @Provides
    @Singleton
    Endpoint provideApiEndpoint(Context context) {
        return new ApiEndpoint(context);
    }
}
