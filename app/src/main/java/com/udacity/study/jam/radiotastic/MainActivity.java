package com.udacity.study.jam.radiotastic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.udacity.study.jam.radiotastic.category.CategoryListFragment;
import com.udacity.study.jam.radiotastic.detail.DetailFragment;
import com.udacity.study.jam.radiotastic.station.StationListFragment;
import com.udacity.study.jam.radiotastic.station.StationsActivity;


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
    public void onCategorySelected(double categoryID) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.sub_content, StationListFragment.init((int) categoryID))
                    .commit();
        } else {
            Intent newIntent = new Intent(this, StationsActivity.class);
            newIntent.putExtra(StationsActivity.CATEGORY_ID_EXTRA, categoryID);
            startActivity(newIntent);
        }
    }

    @Override
    public void onStationSelected(double stationID) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_content, DetailFragment.init((int) stationID))
                    .commit();
        }
    }

}
