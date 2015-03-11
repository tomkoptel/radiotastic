/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.presenter;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;

import com.github.pwittchen.networkevents.library.NetworkEvents;
import com.squareup.otto.Bus;
import com.udacity.study.jam.radiotastic.ApplicationComponent;
import com.udacity.study.jam.radiotastic.db.station.StationColumns;
import com.udacity.study.jam.radiotastic.db.station.StationSelection;
import com.udacity.study.jam.radiotastic.domain.ImmediateSyncCase;
import com.udacity.study.jam.radiotastic.domain.ObserveSyncStateCase;
import com.udacity.study.jam.radiotastic.sync.SyncStationsCaseImpl;
import com.udacity.study.jam.radiotastic.util.NetworkStateManager;

import javax.inject.Inject;

public class StationPresenter extends Presenter implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {

    private static final int LOAD_STATIONS = 200;
    private static final String[] ALL_COLUMNS = {
            StationColumns._ID,
            StationColumns.CATEGORY_ID,
            StationColumns.STATION_ID,
            StationColumns.NAME,
            StationColumns.WEBSITE,
            StationColumns.STREAMURL,
            StationColumns.COUNTRY,
            StationColumns.BITRATE,
            StationColumns.STATUS,
    };

    private final Fragment mFragment;
    private String categoryId;
    private NetworkEvents networkEvents;
    private boolean mSyncIsActive;
    private View mView;
    private Bus bus;

    @Inject
    ImmediateSyncCase immediateSync;
    @Inject
    ObserveSyncStateCase observeSyncCase;
    @Inject
    NetworkStateManager networkStateManager;

    public StationPresenter(Fragment fragment, View view) {
        mFragment = fragment;
        mView = view;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void initialize() {
        ensureCategoryIdPresents();
        ApplicationComponent.Initializer
                .init(mFragment.getActivity()).inject(this);
        loadStations();
        initNetworkListeners();
        initSyncListener();
    }

    @Override
    public void resume() {
        observeSyncCase.resume();
        bus.register(this);
        networkEvents.register();
    }

    @Override
    public void pause() {
        observeSyncCase.pause();
        bus.unregister(this);
        networkEvents.unregister();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        StationSelection selection = new StationSelection();
        selection.categoryId(Long.valueOf(categoryId));
        return new CursorLoader(mFragment.getActivity(),
                StationColumns.CONTENT_URI,
                ALL_COLUMNS,
                selection.sel(),
                new String[] {categoryId},
                StationColumns.NAME + " ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            showStations(data);
        } else {
            startSyncIfPossible();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onRefresh() {
        startSyncIfPossible();
    }

    private void loadStations() {
        mFragment.getLoaderManager().initLoader(LOAD_STATIONS, null, this);
    }

    private void initSyncListener() {
        observeSyncCase.create(new ObserveSyncStateCase.SyncStatusCallBack() {
            @Override
            public void onStatusChanged(final boolean syncIsActive) {
                mSyncIsActive = syncIsActive;
                mFragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (syncIsActive) {
                            if (!mView.isAlreadyLoaded()) {
                                mView.showLoading();
                            }
                        } else {
                            if (mView.isAlreadyLoaded()) {
                                mView.hideLoading();
                            } else {
                                mView.showEmptyCase();
                            }
                        }
                    }
                });
            }
        });
    }

    private void initNetworkListeners() {
        bus = new Bus();
        networkEvents = new NetworkEvents(mFragment.getActivity(), bus);
    }

    private void startSyncIfPossible() {
        if (!mSyncIsActive) {
            Bundle args = new Bundle();
            args.putString(SyncStationsCaseImpl.CATEGORY_ID_ARG, categoryId);
            immediateSync.start(args);
        }

        if (!networkStateManager.isConnectedOrConnecting()) {
            notifyConnectionError();
        }
    }

    private void notifyConnectionError() {
        if (mView.isReady() && !mView.isAlreadyLoaded()) {
            mView.hideLoading();
            mView.showConnectionErrorMessage();
            mView.showEmptyCase();
        }
    }

    private void showStations(Cursor cursor) {
        if (mView.isReady()) {
            mView.renderStations(cursor);
            mView.hideLoading();
        }
    }

    private void ensureCategoryIdPresents() {
        if (categoryId == null) {
            throw new IllegalStateException("Remember setup category id");
        }
    }

    public interface View {
        void hideLoading();

        void showLoading();

        void renderStations(Cursor cursor);

        void showConnectionErrorMessage();

        void showEmptyCase();

        boolean isReady();

        boolean isAlreadyLoaded();
    }
}
