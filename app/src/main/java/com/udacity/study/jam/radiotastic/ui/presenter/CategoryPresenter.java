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

import com.github.pwittchen.networkevents.library.ConnectivityStatus;
import com.github.pwittchen.networkevents.library.NetworkEvents;
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.udacity.study.jam.radiotastic.ApplicationComponent;
import com.udacity.study.jam.radiotastic.db.category.CategoryColumns;
import com.udacity.study.jam.radiotastic.domain.ImmediateSyncCase;
import com.udacity.study.jam.radiotastic.domain.ObserveSyncStateCase;
import com.udacity.study.jam.radiotastic.util.NetworkStateManager;

import javax.inject.Inject;

public class CategoryPresenter extends Presenter implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {

    private static final int LOAD_CATEGORIES = 100;
    private final Fragment mFragment;
    private NetworkEvents networkEvents;
    private View view;
    private Bus bus;

    @Inject
    ImmediateSyncCase immediateSync;
    @Inject
    ObserveSyncStateCase observeSyncCase;
    @Inject
    NetworkStateManager networkStateManager;

    public CategoryPresenter(Fragment fragment, View view) {
        mFragment = fragment;
        this.view = view;
    }

    @Override
    public void initialize() {
        ApplicationComponent.Initializer
                .init(mFragment.getActivity()).inject(this);
        loadCategories();
        initNetworkListeners();
        initSyncListener();
    }

    private void initSyncListener() {
        observeSyncCase.create(new ObserveSyncStateCase.SyncStatusCallBack() {
            @Override
            public void onStatusChanged(final boolean syncIsActive) {
               mFragment.getActivity().runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       if (syncIsActive) {
                           view.showLoading();
                       } else {
                           if (view.isAlreadyLoaded()) {
                               view.hideLoading();
                           } else {
                               view.showEmptyCase();
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

    private void loadCategories() {
        mFragment.getLoaderManager().initLoader(LOAD_CATEGORIES, null, this);
    }

    @Subscribe
    public void onConnectivityChanged(ConnectivityChanged event) {
        if (event.getConnectivityStatus() == ConnectivityStatus.OFFLINE) {
            notifyConnectionError();
        }
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
        return new CursorLoader(mFragment.getActivity(), CategoryColumns.CONTENT_URI,
                CategoryColumns.ALL_COLUMNS, null, null, CategoryColumns.NAME + " ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            showCategories(data);
        } else {
            startSyncIfPossible();
        }
    }

    private void startSyncIfPossible() {
        immediateSync.start(null);
        if (!networkStateManager.isConnectedOrConnecting()) {
            notifyConnectionError();
        }
    }

    private void notifyConnectionError() {
        if (view.isReady() && !view.isAlreadyLoaded()) {
            view.hideLoading();
            view.showConnectionErrorMessage();
            view.showEmptyCase();
        }
    }

    private void showCategories(Cursor cursor) {
        if (view.isReady()) {
            view.renderCategories(cursor);
            view.hideLoading();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onRefresh() {
        startSyncIfPossible();
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
}
