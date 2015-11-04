package com.app.radiotastic.presentation.presenter;

import com.app.radiotastic.domain.Station;
import com.app.radiotastic.domain.interactor.SimpleSubscriber;
import com.app.radiotastic.domain.interactor.UseCase;
import com.app.radiotastic.internal.di.PerActivity;
import com.app.radiotastic.presentation.mappers.StationModelDataMapper;
import com.app.radiotastic.presentation.view.StationListView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Tom Koptel
 */
@PerActivity
public final class StationPresenterList implements Presenter {
    private StationListView mView;

    private final StationModelDataMapper mDataMapper;
    private final UseCase mGetStationList;

    @Inject
    public StationPresenterList(@Named("stationList") UseCase getStationList, StationModelDataMapper dataMapper) {
        mDataMapper = dataMapper;
        mGetStationList = getStationList;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        mGetStationList.unsubscribe();
    }

    public void setView(StationListView view) {
        mView = view;
    }

    public void init() {
        loadStations();
    }

    private void loadStations() {
        mView.hideRetry();
        mView.showLoading();
        fetchUserList();
    }

    private void fetchUserList() {
        mGetStationList.execute(new StationListSubscriber());
    }

    private void showStationsCollectionInView(List<Station> stations) {
        mView.renderStationList(mDataMapper.transform(stations));
    }

    private class StationListSubscriber extends SimpleSubscriber<List<Station>> {
        @Override
        public void onCompleted() {
            mView.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            mView.hideLoading();
            mView.showError(e.getMessage());
            mView.showRetry();
        }

        @Override
        public void onNext(List<Station> stations) {
            showStationsCollectionInView(stations);
        }
    }
}
