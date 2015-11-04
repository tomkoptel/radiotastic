package com.app.radiotastic.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.app.radiotastic.R;
import com.app.radiotastic.internal.di.HasComponent;
import com.app.radiotastic.internal.di.components.DaggerStationsComponent;
import com.app.radiotastic.internal.di.components.StationsComponent;

public class StartupPage extends BaseActivity implements HasComponent<StationsComponent> {

    private StationsComponent mStationComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStationComponent();

        setContentView(R.layout.activity_startup_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initStationComponent() {
        mStationComponent = DaggerStationsComponent.builder()
                .activityModule(getActivityModule())
                .appComponent(getAppComponent())
                .build();
    }

    @Override
    public StationsComponent getComponent() {
        return mStationComponent;
    }
}
