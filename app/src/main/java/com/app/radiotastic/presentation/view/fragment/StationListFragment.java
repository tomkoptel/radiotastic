package com.app.radiotastic.presentation.view.fragment;

import android.os.Bundle;

import com.app.radiotastic.internal.di.components.StationsComponent;
import com.app.radiotastic.presentation.model.StationModel;
import com.app.radiotastic.presentation.presenter.StationPresenterList;
import com.app.radiotastic.presentation.view.StationListView;

import java.util.Collection;

import javax.inject.Inject;

/**
 * @author Tom Koptel
 */
public final class StationListFragment extends BaseFragment implements StationListView {

    @Inject
    StationPresenterList mStationPresenterList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(StationsComponent.class).inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        loadStations();
    }

    private void init() {
        getComponent(StationsComponent.class).inject(this);
        mStationPresenterList.setView(this);
    }

    private void loadStations() {
        mStationPresenterList.init();
    }

    @Override
    public void onResume() {
        super.onResume();
        mStationPresenterList.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mStationPresenterList.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mStationPresenterList.destroy();
    }

    @Override
    public void renderStationList(Collection<StationModel> stationModels) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }
}
