/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.udacity.study.jam.radiotastic.MainApplication;
import com.udacity.study.jam.radiotastic.di.Dagger_SyncComponent;
import com.udacity.study.jam.radiotastic.di.SyncComponent;

public class RadiotasticSyncAdapter extends AbstractThreadedSyncAdapter {
    private final SyncComponent mSyncComponent;

    public RadiotasticSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        MainApplication.component(context);
        mSyncComponent =
                Dagger_SyncComponent.builder()
                        .applicationComponent(MainApplication.component(context))
                        .build();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        mSyncComponent.syncAccountUseCase().execute();
        mSyncComponent.immediateSyncUseCase().execute();
    }
}
