/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.sync.internal;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.udacity.study.jam.radiotastic.App;
import com.udacity.study.jam.radiotastic.di.component.SyncComponent;
import com.udacity.study.jam.radiotastic.sync.SyncHelper;
import com.udacity.study.jam.radiotastic.sync.SyncStationsCaseImpl;

public class RadiotasticSyncAdapter extends AbstractThreadedSyncAdapter {
    public RadiotasticSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        if (extras.containsKey(SyncHelper.SYNC_EXTRAS_FLAG)) {
            SyncComponent syncComponent = App.get(getContext()).syncGraph(syncResult, extras);
            if (extras.containsKey(SyncStationsCaseImpl.CATEGORY_ID_ARG)) {
                syncComponent.stationsSync().execute(extras);
            } else {
                syncComponent.categoriesSync().execute(extras);
            }
        }
    }
}
