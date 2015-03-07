/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.data;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.domain.GetAccountUseCase;
import com.udacity.study.jam.radiotastic.domain.ImmediateSyncUseCase;

import javax.inject.Inject;

public class ImmediateSyncUseCaseImpl implements ImmediateSyncUseCase {

    private final GetAccountUseCase mGetAccountUseCase;
    @Inject Context mContext;

    @Inject
    public ImmediateSyncUseCaseImpl(GetAccountUseCase getAccountUseCase) {
        mGetAccountUseCase = getAccountUseCase;
    }
    @Override
    public void execute() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(mGetAccountUseCase.execute(),
                mContext.getString(R.string.content_authority), bundle);
    }

}
