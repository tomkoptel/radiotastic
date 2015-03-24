/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic;

import android.app.Application;
import android.content.Context;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import com.udacity.study.jam.radiotastic.di.component.SyncComponent;
import com.udacity.study.jam.radiotastic.util.CrashReportingTree;
import com.udacity.study.jam.radiotastic.util.DebugTree;

import timber.log.Timber;

public class App extends Application {
    private Graph graphComponent;
    private boolean useMock;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }

        graphComponent = Graph.Initializer.init(this, false);

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    public void setMockMode(boolean useMock) {
        this.useMock = useMock;
        this.graphComponent = null;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public SyncComponent syncGraph(SyncResult syncResult, Bundle extras) {
        return SyncComponent.Initializer.init(this, syncResult, extras, useMock);
    }

    public Graph graph() {
        if (graphComponent == null) {
            graphComponent = Graph.Initializer.init(this, useMock);
        }
        return graphComponent;
    }
}
