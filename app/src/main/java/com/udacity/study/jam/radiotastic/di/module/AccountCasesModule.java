/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.di.module;

import com.udacity.study.jam.radiotastic.data.GetAccountUseCaseImpl;
import com.udacity.study.jam.radiotastic.domain.GetAccountUseCase;

import dagger.Module;
import dagger.Provides;

@Module
final public class AccountCasesModule {
    @Provides
    public GetAccountUseCase provideGetAccountUseCase() {
        return new GetAccountUseCaseImpl();
    }
}