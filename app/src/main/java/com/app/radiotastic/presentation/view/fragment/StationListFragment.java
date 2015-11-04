package com.app.radiotastic.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.app.radiotastic.R;
import com.app.radiotastic.internal.di.components.StationsComponent;
import com.app.radiotastic.presentation.model.StationModel;
import com.app.radiotastic.presentation.presenter.StationPresenterList;
import com.app.radiotastic.presentation.view.StationListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Tom Koptel
 */
public final class StationListFragment extends BaseFragment implements StationListView {

    @Inject
    StationPresenterList mStationPresenterList;

    @Bind(R.id.stationList)
    ListView listView;
    @Bind(R.id.messageView)
    TextView messageView;
    @Bind(R.id.retryControl)
    Button retryControl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

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
    public void renderStationList(List<StationModel> stationModels) {
        ArrayAdapter<StationModel> arrayAdapter = new ArrayAdapter<StationModel>(getActivity(),
                android.R.layout.simple_expandable_list_item_1, stationModels);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void showLoading() {
        messageView.setVisibility(View.VISIBLE);
        messageView.setText("Loading...");
    }

    @Override
    public void hideLoading() {
        messageView.setVisibility(View.GONE);
        messageView.setText(null);
    }

    @Override
    public void showRetry() {
        retryControl.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        retryControl.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        messageView.setVisibility(View.VISIBLE);
        messageView.setText(message);
    }
}
