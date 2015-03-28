/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.activity;

import android.os.Bundle;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.ui.fragment.StationListFragment;
import com.udacity.study.jam.radiotastic.ui.fragment.StationListFragment_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;


@EActivity(R.layout.activity_stations)
public class StationsActivity extends BaseActivity implements StationListFragment.Callback {

    @Extra
    protected String categoryId;
    @Extra
    protected String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content,
                            StationListFragment_
                                    .builder()
                                    .categoryId(categoryId)
                                    .categoryName(categoryName)
                                    .build())
                    .commit();
        }
    }

    @Override
    public void onStationSelected(String stationID, String stationName, String streamUrl) {
        DetailActivity_.intent(this)
                .stationId(stationID)
                .stationName(stationName)
                .streamUrl(streamUrl)
                .start();
    }

}
