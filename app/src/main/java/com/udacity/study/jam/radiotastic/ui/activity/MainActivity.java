package com.udacity.study.jam.radiotastic.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.ui.fragment.CategoryListFragment;
import com.udacity.study.jam.radiotastic.ui.fragment.CategoryListFragment_;
import com.udacity.study.jam.radiotastic.ui.fragment.StationFragment_;
import com.udacity.study.jam.radiotastic.ui.fragment.StationListFragment;
import com.udacity.study.jam.radiotastic.ui.fragment.StationListFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity
        implements CategoryListFragment.Callback, StationListFragment.Callback {

    @ViewById
    protected Toolbar toolbar;

    private boolean mTwoPane;
    private Bundle mSavedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
    }

    @AfterViews
    final void init() {
        setSupportActionBar(toolbar);
        mTwoPane = (findViewById(R.id.detail_content) != null);

        if (mSavedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.master_content, CategoryListFragment_.builder().build())
                    .commit();
        }
    }

    @Override
    public void onCategorySelected(String categoryID, String categoryName) {
        if (mTwoPane) {
            getSupportActionBar().setTitle(categoryName);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.master_content,
                            StationListFragment_.builder()
                                    .categoryId(categoryID)
                                    .categoryName(categoryName)
                                    .build())
                    .addToBackStack(null)
                    .commit();
        } else {
            StationsActivity_.intent(this)
                    .categoryId(categoryID)
                    .categoryName(categoryName)
                    .start();
        }
    }

    @Override
    public void onStationSelected(String stationID, String stationName, String streamUrl) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_content,
                            StationFragment_.builder()
                            .stationId(stationID)
                            .stationName(stationName)
                            .streamUrl(streamUrl)
                            .build())
                    .commit();
        }
    }

}
