/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.presenter;

import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;

import com.udacity.study.jam.radiotastic.App;
import com.udacity.study.jam.radiotastic.db.category.CategoryColumns;
import com.udacity.study.jam.radiotastic.domain.ImmediateSyncCase;
import com.udacity.study.jam.radiotastic.domain.ObserveSyncStateCase;
import com.udacity.study.jam.radiotastic.util.NetworkStateManager;

import javax.inject.Inject;

public class CategoriesPresenter extends Presenter implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {

    private static final int LOAD_CATEGORIES = 100;
    private final Fragment mFragment;
    private boolean mSyncIsActive;
    private View mView;
    private SyncType syncType;

    @Inject
    ImmediateSyncCase immediateSync;
    @Inject
    ObserveSyncStateCase observeSyncCase;
    @Inject
    NetworkStateManager networkStateManager;

    public CategoriesPresenter(Fragment fragment, View view) {
        mFragment = fragment;
        mView = view;
    }

    @Override
    public void initialize() {
        App.get(mFragment.getActivity()).graph().inject(this);
        loadCategories();
        initSyncListener();
    }

    @Override
    public void resume() {
        observeSyncCase.resume();
    }

    @Override
    public void pause() {
        observeSyncCase.pause();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mFragment.getActivity(), CategoryColumns.CONTENT_URI,
                CategoryColumns.ALL_COLUMNS, null, null, CategoryColumns.NAME + " ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            showCategories(data);
        } else {
            showEmptyView();
            startCachedSyncIfPossible();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onRefresh() {
        startRemoteSyncIfPossible();
    }

    private void initSyncListener() {
        observeSyncCase.create(new SyncStatusObserver() {
            @Override
            public void onStatusChanged(int which) {
                mSyncIsActive = observeSyncCase.isSyncActive();
                if (syncType == SyncType.FROM_CLOUD) {
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
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void loadCategories() {
        mFragment.getLoaderManager().initLoader(LOAD_CATEGORIES, null, this);
    }

    private void startRemoteSyncIfPossible() {
        if (!mSyncIsActive) {
            immediateSync.startRemoteSync(null);
        }
        if (!networkStateManager.isConnectedOrConnecting()) {
            notifyConnectionError();
        }
    }

    private void startCachedSyncIfPossible() {
        if (!mSyncIsActive) {
            immediateSync.startCachedSync(null);
        }
    }

    private void notifyConnectionError() {
        if (mView.isReady() && !mView.isAlreadyLoaded()) {
            mView.hideLoading();
            mView.showConnectionErrorMessage();
            mView.showEmptyCase();
        }
    }

    private void showCategories(Cursor cursor) {
        if (mView.isReady()) {
            mView.renderCategories(cursor);
            mView.hideLoading();
        }
    }

    private void showEmptyView() {
        mFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.showEmptyCase();
            }
        });
    }

    private void runOnUiThread(Runnable runnable) {
        mFragment.getActivity().runOnUiThread(runnable);
    }

    public interface View {
        void hideLoading();

        void showLoading();

        void renderCategories(Cursor cursor);

        void showConnectionErrorMessage();

        void showEmptyCase();

        boolean isReady();

        boolean isAlreadyLoaded();
    }

    private enum SyncType {
        FROM_FILE_SYSTEM, FROM_CLOUD;
    }
}
