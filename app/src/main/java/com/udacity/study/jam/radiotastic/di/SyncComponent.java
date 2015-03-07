/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.di;

import com.udacity.study.jam.radiotastic.domain.ImmediateSyncUseCase;
import com.udacity.study.jam.radiotastic.domain.SyncAccountUseCase;

import dagger.Component;

@Component(
        modules = {SyncCasesModule.class},
        dependencies = {ApplicationComponent.class, AccountCasesModule.class}
)
public interface SyncComponent {
    SyncAccountUseCase syncAccountUseCase();
    ImmediateSyncUseCase immediateSyncUseCase();
}
