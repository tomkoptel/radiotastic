/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.di.module;

import android.content.Context;
import android.content.SyncResult;

import com.udacity.study.jam.radiotastic.api.RadioApi;
import com.udacity.study.jam.radiotastic.sync.SyncCategoriesCaseImpl;
import com.udacity.study.jam.radiotastic.domain.SyncCategoriesCase;

import dagger.Module;
import dagger.Provides;

@Module
public class SyncModule {
    private final SyncResult syncResult;

    public SyncModule(SyncResult syncResult) {
        this.syncResult = syncResult;
    }

    @Provides
    SyncCategoriesCase provideSyncCategoriesCase(Context context, RadioApi radioApi) {
        return new SyncCategoriesCaseImpl(context, syncResult, radioApi);
    }
}
