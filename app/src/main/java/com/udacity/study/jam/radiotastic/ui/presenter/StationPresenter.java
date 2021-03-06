/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.presenter;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.udacity.study.jam.radiotastic.SongItem;
import com.udacity.study.jam.radiotastic.StationDetails;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataColumns;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataCursor;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataSelection;
import com.udacity.study.jam.radiotastic.player.PlayerPref_;
import com.udacity.study.jam.radiotastic.sync.internal.StationSyncService;

import java.util.Calendar;
import java.util.List;

public class StationPresenter extends Presenter
        implements LoaderManager.LoaderCallbacks<Cursor>,
        SharedPreferences.OnSharedPreferenceChangeListener {
    private static final int LOAD_STATION = 300;

    private final PlayerPref_ playerPref;
    private final Fragment mFragment;
    private final View mView;
    private String stationId;

    private final Gson gson = new Gson();

    public StationPresenter(Fragment mFragment, View mView) {
        this.mFragment = mFragment;
        this.mView = mView;

        playerPref = new PlayerPref_(mFragment.getActivity());
    }

    @Override
    public void initialize() {
        ensureStationIdPresents();
        loadStation();
    }

    private void loadStation() {
        mFragment.getLoaderManager().initLoader(LOAD_STATION, null, this);
    }

    private void ensureStationIdPresents() {
        if (stationId == null) {
            throw new IllegalStateException("Remember setup station id");
        }
    }

    @Override
    public void resume() {
        playerPref.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void pause() {
        playerPref.getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        StationMetaDataSelection selection = new StationMetaDataSelection();
        selection.stationId(Long.valueOf(stationId));
        selection.and().createdAtAfterEq(calendar.getTime());

        calendar.set(Calendar.HOUR_OF_DAY, 24);
        selection.and().createdAtBeforeEq(calendar.getTime());

        return new CursorLoader(mFragment.getActivity(),
                StationMetaDataColumns.CONTENT_URI, StationMetaDataColumns.ALL_COLUMNS,
                selection.sel(), selection.args(), null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            showStation(data);
        } else {
            mView.showLoading();
            StationSyncService.performSync(mFragment.getActivity(), stationId);
        }
    }

    private void showStation(Cursor cursor) {
        if (mView.isReady()) {
            if (cursor.moveToFirst()) {
                StationDetails details = transform(cursor);
                if (details == null) {
                    mView.showConnectionErrorMessage();
                } else {
                    List<SongItem> songHistory = details.getSonghistory();

                    mView.hideLoading();
                    if (songHistory.isEmpty()) {
                        mView.showEmptyCase();
                    } else {
                        mView.renderSongHistory(songHistory);
                    }

                    if (!TextUtils.isEmpty(details.getImage())) {
                        mView.renderStationImage(details.getImage());
                    }
                }
            }
        }
    }

    private StationDetails transform(Cursor cursor) {
        StationMetaDataCursor dataCursor = new StationMetaDataCursor(cursor);
        return gson.fromJson(dataCursor.getMeta(), StationDetails.class);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(playerPref.stationId().key())) {
            if ("-1".equals(playerPref.stationId().get())) {
                mView.showPlayControl();
                mView.showPlayerErrorMessage();
            }
        }
    }

    public interface View {
        void hideLoading();

        void showLoading();

        void renderSongHistory(List<SongItem> songHistory);

        void renderStationImage(String imageUri);

        void showConnectionErrorMessage();

        void showPlayerErrorMessage();

        void showEmptyCase();

        boolean isReady();

        void showPlayControl();
    }
}
