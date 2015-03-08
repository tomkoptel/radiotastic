/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.data;

import android.content.Context;
import android.content.SyncResult;

import com.udacity.study.jam.radiotastic.api.RadioApi;
import com.udacity.study.jam.radiotastic.domain.SyncCategoriesCase;

import javax.inject.Inject;

public class SyncCategoriesCaseImpl implements SyncCategoriesCase {
    private final Context context;
    private final SyncResult syncResult;
    private final RadioApi radioApi;

    @Inject
    public SyncCategoriesCaseImpl(Context context,
                                  SyncResult syncResult,
                                  RadioApi radioApi) {
        this.context = context;
        this.syncResult = syncResult;
        this.radioApi = radioApi;
    }

    @Override
    public void run() {
//        radioApi.listPrimaryCategories();
    }
}
