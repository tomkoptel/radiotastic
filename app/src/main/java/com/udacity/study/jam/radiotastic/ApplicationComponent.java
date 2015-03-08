/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic;

import com.udacity.study.jam.radiotastic.di.component.AppGraph;
import com.udacity.study.jam.radiotastic.di.module.SystemServicesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {SystemServicesModule.class}
)
public interface ApplicationComponent extends AppGraph {
    /**
     * An initializer that creates the graph from an application.
     */
    final static class Initializer {
        static AppGraph init(MainApplication app) {
            return Dagger_ApplicationComponent.builder()
                    .systemServicesModule(new SystemServicesModule(app))
                    .build();
        }

        private Initializer() {
        } // No instances.
    }
}
