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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.udacity.study.jam.radiotastic.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.state_layout)
public class StateSyncLayout extends RelativeLayout {
    @ViewById(android.R.id.icon)
    protected ImageView stateImage;
    @ViewById(android.R.id.text1)
    protected TextView stateText;

    public enum Type {
        SYNC, ERROR, EMPTY, NONE;
    }

    public StateSyncLayout(Context context) {
        super(context);
    }

    public StateSyncLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StateSyncLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StateSyncLayout(Context context, AttributeSet attrs,
                           int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setImageType(Type type) {
        setVisibility(View.VISIBLE);
        switch (type) {
            case SYNC:
                stateImage.setImageResource(R.drawable.ic_cloud_sync);
                stateText.setText(R.string.loading);
                break;
            case EMPTY:
                stateImage.setImageResource(R.drawable.ic_empty_data);
                stateText.setText(R.string.no_data);
                break;
            case ERROR:
                stateImage.setImageResource(R.drawable.ic_alert_error);
                stateText.setText(R.string.error);
                break;
            case NONE:
                setVisibility(View.GONE);
                break;
        }
    }
}
