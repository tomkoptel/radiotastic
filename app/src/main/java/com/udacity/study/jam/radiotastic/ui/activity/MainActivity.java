package com.udacity.study.jam.radiotastic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.ui.fragment.CategoryListFragment;
import com.udacity.study.jam.radiotastic.ui.fragment.StationFragment;
import com.udacity.study.jam.radiotastic.ui.fragment.StationListFragment;


public class MainActivity extends ActionBarActivity
        implements CategoryListFragment.Callback, StationListFragment.Callback {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTwoPane = (findViewById(R.id.sub_content) != null);
    }

    @Override
    public void onCategorySelected(String categoryID) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.sub_content, StationListFragment.init(categoryID))
                    .commit();
        } else {
            Intent newIntent = new Intent(this, StationsActivity.class);
            newIntent.putExtra(StationsActivity.CATEGORY_ID_EXTRA, categoryID);
            startActivity(newIntent);
        }
    }

    @Override
    public void onStationSelected(String stationID, String streamUrl) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_content, StationFragment.init(stationID, streamUrl))
                    .commit();
        }
    }

}
