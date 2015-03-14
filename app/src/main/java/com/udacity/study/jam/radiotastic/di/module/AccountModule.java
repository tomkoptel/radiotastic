/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.di.module;

import android.content.Context;

import com.udacity.study.jam.radiotastic.domain.GetAccountCase;
import com.udacity.study.jam.radiotastic.domain.ImmediateSyncCase;
import com.udacity.study.jam.radiotastic.domain.ObserveSyncStateCase;
import com.udacity.study.jam.radiotastic.domain.SyncAccountCase;
import com.udacity.study.jam.radiotastic.sync.GetAccountCaseImpl;
import com.udacity.study.jam.radiotastic.sync.ImmediateSyncCaseImpl;
import com.udacity.study.jam.radiotastic.sync.ObserveSyncStateCaseImpl;
import com.udacity.study.jam.radiotastic.sync.SyncAccountCaseImpl;
import com.udacity.study.jam.radiotastic.sync.SyncHelper;

import dagger.Module;
import dagger.Provides;

@Module
final public class AccountModule {
    @Provides
    GetAccountCase provideGetAccountUseCase(Context context) {
        return new GetAccountCaseImpl(context);
    }

    @Provides
    SyncAccountCase provideSyncAccountUseCaseImpl(
            Context context, GetAccountCase getAccountUseCase) {
        return new SyncAccountCaseImpl(context, getAccountUseCase);
    }

    @Provides
    ImmediateSyncCase provideImmediateSyncUseCase(
            Context context, SyncHelper syncHelper, GetAccountCase getAccountUseCase) {
        return new ImmediateSyncCaseImpl(context, syncHelper, getAccountUseCase);
    }

    @Provides
    ObserveSyncStateCase provideObserveAccountCase(
            Context context, GetAccountCase getAccountUseCase) {
        return new ObserveSyncStateCaseImpl(context, getAccountUseCase);
    }
}
