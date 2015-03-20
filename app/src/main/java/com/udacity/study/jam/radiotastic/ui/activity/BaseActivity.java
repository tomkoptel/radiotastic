/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.udacity.study.jam.radiotastic.BuildConfig;

import org.androidannotations.api.ViewServer;

public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isDevMode()) {
            ViewServer.get(this).addWindow(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isDevMode()) {
            ViewServer.get(this).setFocusedWindow(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isDevMode()) {
            ViewServer.get(this).removeWindow(this);
        }
    }

    protected boolean isDevMode() {
        return BuildConfig.DEBUG;
    }

}
