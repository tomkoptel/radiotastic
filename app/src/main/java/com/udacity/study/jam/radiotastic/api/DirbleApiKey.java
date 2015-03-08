/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.api;

import android.content.Context;

import com.udacity.study.jam.radiotastic.R;

public class DirbleApiKey implements ApiKey {
    private final Context context;

    public DirbleApiKey(Context context) {
        this.context = context;
    }

    public String get() {
        return context.getString(R.string.api_key);
    }
}
