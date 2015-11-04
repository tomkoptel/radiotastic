package com.app.radiotastic.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.app.radiotastic.R;
import com.app.radiotastic.internal.di.HasComponent;
import com.app.radiotastic.internal.di.components.DaggerStationsComponent;
import com.app.radiotastic.internal.di.components.StationsComponent;
import com.app.radiotastic.presentation.view.fragment.StationListFragment;

public class StartupPage extends BaseActivity implements HasComponent<StationsComponent> {

    private StationsComponent mStationComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStationComponent();
        setContentView(R.layout.activity_startup_page);
        initToolbar();
        initStationListView(savedInstanceState);
    }

    private void initStationComponent() {
        mStationComponent = DaggerStationsComponent.builder()
                .activityModule(getActivityModule())
                .appComponent(getAppComponent())
                .build();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initStationListView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            StationListFragment stationListFragment = new StationListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, stationListFragment)
                    .commit();
        }
    }

    @Override
    public StationsComponent getComponent() {
        return mStationComponent;
    }
}
