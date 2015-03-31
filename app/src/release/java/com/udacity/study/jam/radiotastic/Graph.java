/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic;

import android.content.Context;

import com.udacity.study.jam.radiotastic.di.module.AccountModule;
import com.udacity.study.jam.radiotastic.di.module.DataModule;
import com.udacity.study.jam.radiotastic.di.module.SystemServicesModule;
import com.udacity.study.jam.radiotastic.sync.internal.StationSyncService;
import com.udacity.study.jam.radiotastic.ui.activity.MainActivity;
import com.udacity.study.jam.radiotastic.ui.presenter.CategoriesPresenter;
import com.udacity.study.jam.radiotastic.ui.presenter.StationsPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {SystemServicesModule.class, DataModule.class, AccountModule.class}
)
public interface Graph {
    void inject(MainActivity activity);
    void inject(CategoriesPresenter presenter);
    void inject(StationsPresenter presenter);
    void inject(StationSyncService stationSyncService);

    /**
     * An initializer that creates the graph from an application.
     */
    final static class Initializer {
        public static Graph init(Context context, boolean mockMode) {
            return Dagger_Graph.builder()
                    .systemServicesModule(new SystemServicesModule(
                            App.get(context)))
                    .build();
        }

        private Initializer() {
        } // No instances.
    }
}
