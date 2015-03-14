/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.test.sync;

import android.content.ContentResolver;
import android.os.Bundle;
import android.test.AndroidTestCase;

import com.udacity.study.jam.radiotastic.sync.SyncHelper;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SyncHelperTest extends AndroidTestCase {

    private SyncHelper syncHelper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        syncHelper = new SyncHelper();
    }

    public void testShouldPrepareSyncRemoteExtras() {
        Bundle args = syncHelper.prepareRemoteExtras();
        int flag = args.getInt(SyncHelper.SYNC_EXTRAS_FLAG);
        assertThat(flag, is(SyncHelper.FLAG_SYNC_REMOTE));
    }

    public void testShouldPrepareSyncCachedExtras() {
        Bundle args = syncHelper.prepareCachedExtras();
        int flag = args.getInt(SyncHelper.SYNC_EXTRAS_FLAG);
        assertThat(flag, is(SyncHelper.FLAG_SYNC_CACHED));
    }

    public void testShouldPrepareManualSyncArgs() {
        Bundle args = syncHelper.prepareManualSyncExtras();
        assertThat(args.containsKey(ContentResolver.SYNC_EXTRAS_EXPEDITED), is(true));
        assertThat(args.containsKey(ContentResolver.SYNC_EXTRAS_MANUAL), is(true));
    }

}
