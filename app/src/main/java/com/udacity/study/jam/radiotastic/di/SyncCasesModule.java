/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.di;

import com.udacity.study.jam.radiotastic.data.ImmediateSyncUseCaseImpl;
import com.udacity.study.jam.radiotastic.data.SyncAccountUseCaseImpl;
import com.udacity.study.jam.radiotastic.domain.GetAccountUseCase;
import com.udacity.study.jam.radiotastic.domain.ImmediateSyncUseCase;
import com.udacity.study.jam.radiotastic.domain.SyncAccountUseCase;

import dagger.Module;
import dagger.Provides;

@Module
final public class SyncCasesModule {

    @Provides
    public SyncAccountUseCase provideSyncAccountUseCase(GetAccountUseCase getAccountUseCase) {
        return new SyncAccountUseCaseImpl(getAccountUseCase);
    }

    @Provides
    public ImmediateSyncUseCase provideImmediateSyncUseCase(GetAccountUseCase getAccountUseCase) {
        return new ImmediateSyncUseCaseImpl(getAccountUseCase);
    }

}
