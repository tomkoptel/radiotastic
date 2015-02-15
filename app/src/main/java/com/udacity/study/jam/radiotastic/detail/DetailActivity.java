/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.detail;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.udacity.study.jam.radiotastic.R;

public class DetailActivity extends ActionBarActivity {

    public static final String STATION_ID_EXTRA = "stationID";
    private int mStationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getIntent() != null && getIntent().hasExtra(STATION_ID_EXTRA)) {
            mStationID = getIntent().getIntExtra(STATION_ID_EXTRA, 0);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, DetailFragment.init(mStationID))
                    .commit();
        }
    }

}
