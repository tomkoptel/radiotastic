/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.sync;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncInfo;
import android.content.SyncStatusObserver;
import android.os.Build;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.domain.GetAccountCase;
import com.udacity.study.jam.radiotastic.domain.ObserveSyncStateCase;

import javax.inject.Inject;

public class ObserveSyncStateCaseImpl implements ObserveSyncStateCase {

    private final Context context;
    private final GetAccountCase getAccountCase;
    private Object syncHandler;
    private SyncStatusObserver observer;

    @Inject
    public ObserveSyncStateCaseImpl(Context context, GetAccountCase getAccountCase) {
        this.context = context;
        this.getAccountCase = getAccountCase;
    }

    @Override
    public void create(final SyncStatusCallBack statusCallBack) {
        observer = new SyncStatusObserver() {
            @Override
            public void onStatusChanged(int which) {
                statusCallBack.onStatusChanged(isSyncActive());
            }
        };
    }

    @Override
    public void resume() {
        // Refresh synchronization status
        observer.onStatusChanged(0);

        // Watch for synchronization status changes
        final int mask = ContentResolver.SYNC_OBSERVER_TYPE_PENDING |
                ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE;
        syncHandler = ContentResolver.addStatusChangeListener(mask, observer);
    }

    @Override
    public void pause() {
        // Remove our synchronization listener if registered
        if (syncHandler != null) {
            ContentResolver.removeStatusChangeListener(syncHandler);
            syncHandler = null;
        }
    }

    private boolean isSyncActive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return isSyncActiveHoneycomb();
        } else {
            return isSyncActiveGingerbread();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private boolean isSyncActiveGingerbread() {
        String authority = context.getString(R.string.content_authority);
        SyncInfo syncInfo = ContentResolver.getCurrentSync();
        return (syncInfo != null
                && syncInfo.account.equals(getAccountCase.get())
                && syncInfo.authority.equals(authority));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private boolean isSyncActiveHoneycomb() {
        String authority = context.getString(R.string.content_authority);

        for (SyncInfo syncInfo : ContentResolver.getCurrentSyncs()) {
            if (syncInfo.account.equals(getAccountCase.get()) &&
                    syncInfo.authority.equals(authority)) {
                return true;
            }
        }
        return false;
    }

}
