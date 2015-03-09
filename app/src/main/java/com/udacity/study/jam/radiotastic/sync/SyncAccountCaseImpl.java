/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.sync;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.os.Build;
import android.os.Bundle;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.domain.GetAccountCase;
import com.udacity.study.jam.radiotastic.domain.SyncAccountCase;

import javax.inject.Inject;

public class SyncAccountCaseImpl implements SyncAccountCase {

    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    private final GetAccountCase getAccountUseCase;
    private final Context context;

    @Inject
    public SyncAccountCaseImpl(Context context, GetAccountCase getAccountUseCase) {
        this.context = context;
        this.getAccountUseCase = getAccountUseCase;
    }

    @Override
    public void start() {
        Account account = getAccountUseCase.get();
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            syncForKitkat(account, authority);
        } else {
            ContentResolver.addPeriodicSync(account, authority, new Bundle(), SYNC_INTERVAL);
        }
        ContentResolver.setSyncAutomatically(account, authority, true);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void syncForKitkat(Account account, String authority) {
        SyncRequest syncRequest = new SyncRequest.Builder()
                .syncPeriodic(SYNC_INTERVAL, SYNC_FLEXTIME)
                .setSyncAdapter(account, authority)
                .setExtras(new Bundle())
                .build();
        ContentResolver.requestSync(syncRequest);
    }

}
