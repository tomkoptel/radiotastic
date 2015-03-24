/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.udacity.study.jam.radiotastic.R;

import org.androidannotations.annotations.EView;

@EView
public class DataImageView extends ImageView {
    public enum Type {
        SYNC, ERROR, EMPTY, NONE;
    }

    public DataImageView(Context context) {
        super(context);
    }

    public DataImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DataImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DataImageView(Context context, AttributeSet attrs,
                         int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setImageType(Type type) {
        setVisibility(View.VISIBLE);
        switch (type) {
            case SYNC:
                setImageResource(R.drawable.ic_cloud_sync);
                break;
            case EMPTY:
                setImageResource(R.drawable.ic_empty_data);
                break;
            case ERROR:
                setImageResource(R.drawable.ic_alert_error);
                break;
            case NONE:
                setVisibility(View.GONE);
                break;
        }
    }
}
