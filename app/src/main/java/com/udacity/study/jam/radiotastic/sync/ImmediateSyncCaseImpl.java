/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.sync;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.domain.GetAccountCase;
import com.udacity.study.jam.radiotastic.domain.ImmediateSyncCase;

import javax.inject.Inject;

public class ImmediateSyncCaseImpl implements ImmediateSyncCase {

    private final GetAccountCase getAccountUseCase;
    private final SyncHelper syncHelper;
    private final Context context;

    @Inject
    public ImmediateSyncCaseImpl(Context context, SyncHelper syncHelper, GetAccountCase getAccountUseCase) {
        this.context = context;
        this.syncHelper = syncHelper;
        this.getAccountUseCase = getAccountUseCase;
    }

    @Override
    public void startCachedSync(Bundle args) {
        Bundle extras = combineArgs(args, syncHelper.prepareCachedExtras());
        requestSync(extras);
    }

    @Override
    public void startRemoteSync(Bundle args) {
        Bundle extras = combineArgs(args, syncHelper.prepareRemoteExtras());
        requestSync(extras);
    }

    private Bundle combineArgs(Bundle args, Bundle preparedArgs) {
        if (args != null) {
            preparedArgs.putAll(args);
        }
        return preparedArgs;
    }

    private void requestSync(Bundle args) {
        ContentResolver.requestSync(getAccountUseCase.get(),
                context.getString(R.string.content_authority), args);
    }
}
