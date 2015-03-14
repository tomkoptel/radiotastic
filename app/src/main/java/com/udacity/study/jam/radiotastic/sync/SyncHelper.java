/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.sync;

import android.content.ContentResolver;
import android.os.Bundle;

import javax.inject.Inject;

public class SyncHelper {
    public static final String SYNC_EXTRAS_FLAG = "SYNC_EXTRAS_FLAG";
    public static final int FLAG_SYNC_REMOTE = 0x4;
    public static final int FLAG_SYNC_CACHED = 0x8;

    @Inject
    public SyncHelper() {
    }

    public Bundle prepareRemoteExtras() {
        Bundle args = prepareManualSyncExtras();
        args.putInt(SYNC_EXTRAS_FLAG, FLAG_SYNC_REMOTE);
        return args;
    }

    public Bundle prepareCachedExtras() {
        Bundle args = prepareManualSyncExtras();
        args.putInt(SYNC_EXTRAS_FLAG, FLAG_SYNC_CACHED);
        return args;
    }

    public Bundle prepareManualSyncExtras() {
        Bundle args = new Bundle();
        args.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        args.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        return args;
    }
}
