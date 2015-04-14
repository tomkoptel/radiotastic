/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.di.component;

import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.udacity.study.jam.radiotastic.App;
import com.udacity.study.jam.radiotastic.di.module.DebugDataModule;
import com.udacity.study.jam.radiotastic.di.module.SyncModule;
import com.udacity.study.jam.radiotastic.di.module.SystemServicesModule;
import com.udacity.study.jam.radiotastic.domain.SyncCategoriesCase;
import com.udacity.study.jam.radiotastic.domain.SyncStationsCase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                SyncModule.class,
                SystemServicesModule.class,
                DebugDataModule.class,
        }
)
public interface SyncComponent {
    SyncCategoriesCase categoriesSync();
    SyncStationsCase stationsSync();

    final public static class Initializer {
        static public SyncComponent init(Context context,
                                         SyncResult syncResult,
                                         Bundle extras,
                                         boolean mockMode) {
            return DaggerSyncComponent.builder()
                    .systemServicesModule(new SystemServicesModule(
                            App.get(context)))
                    .syncModule(new SyncModule(syncResult))
                    .debugDataModule(new DebugDataModule(mockMode, extras))
                    .build();
        }

        private Initializer() {
        } // No instances.
    }
}
