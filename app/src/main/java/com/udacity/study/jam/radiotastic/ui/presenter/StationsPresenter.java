/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.presenter;

import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;

import com.udacity.study.jam.radiotastic.App;
import com.udacity.study.jam.radiotastic.db.station.StationColumns;
import com.udacity.study.jam.radiotastic.db.station.StationSelection;
import com.udacity.study.jam.radiotastic.domain.ImmediateSyncCase;
import com.udacity.study.jam.radiotastic.domain.ObserveSyncStateCase;
import com.udacity.study.jam.radiotastic.sync.SyncStationsCaseImpl;
import com.udacity.study.jam.radiotastic.ui.UiPref_;
import com.udacity.study.jam.radiotastic.util.NetworkStateManager;

import javax.inject.Inject;

public class StationsPresenter extends Presenter
        implements LoaderManager.LoaderCallbacks<Cursor>,
        SwipeRefreshLayout.OnRefreshListener, SharedPreferences.OnSharedPreferenceChangeListener {

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
    private final UiPref_ uiPref;
    private String categoryId;
    private boolean mSyncIsActive;
    private View mView;

    @Inject
    ImmediateSyncCase immediateSync;
    @Inject
    ObserveSyncStateCase observeSyncCase;
    @Inject
    NetworkStateManager networkStateManager;

    public StationsPresenter(Fragment fragment, View view) {
        mFragment = fragment;
        mView = view;
        uiPref = new UiPref_(fragment.getActivity());
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void initialize() {
        ensureCategoryIdPresents();
        App.get(mFragment.getActivity()).graph().inject(this);
        loadStations();
        initSyncListener();
    }

    @Override
    public void resume() {
        uiPref.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        observeSyncCase.resume();
    }

    @Override
    public void pause() {
        uiPref.getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        observeSyncCase.pause();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        StationSelection selection = new StationSelection();
        selection.categoryId(Long.valueOf(categoryId));
        return new CursorLoader(mFragment.getActivity(),
                StationColumns.CONTENT_URI,
                ALL_COLUMNS,
                selection.sel(),
                new String[]{categoryId},
                uiPref.sortOption().get() + " " + uiPref.sortOrder().get());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            showStations(data);
        } else {
            showEmptyView();
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
        observeSyncCase.create(new SyncStatusObserver() {
            @Override
            public void onStatusChanged(int which) {
                mSyncIsActive = observeSyncCase.isSyncActive();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mSyncIsActive) {
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

    private void startSyncIfPossible() {
        if (!mSyncIsActive) {
            Bundle args = new Bundle();
            args.putString(SyncStationsCaseImpl.CATEGORY_ID_ARG, categoryId);
            immediateSync.startRemoteSync(args);
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

    private void showEmptyView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.showEmptyCase();
            }
        });
    }

    private void runOnUiThread(Runnable runnable) {
        mFragment.getActivity().runOnUiThread(runnable);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(uiPref.sortOption().key())) {
            mFragment.getLoaderManager().restartLoader(LOAD_STATIONS, null, this);
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
