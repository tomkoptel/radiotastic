/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kenny.snackbar.SnackBar;
import com.kenny.snackbar.SnackBarItem;
import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.db.station.StationCursor;
import com.udacity.study.jam.radiotastic.ui.adapter.decoration.DividerItemDecoration;
import com.udacity.study.jam.radiotastic.ui.adapter.easy.EasyCursorRecyclerAdapter;
import com.udacity.study.jam.radiotastic.ui.adapter.easy.EasyViewHolder;
import com.udacity.study.jam.radiotastic.ui.adapter.holder.StationViewHolder;
import com.udacity.study.jam.radiotastic.ui.presenter.StationsPresenter;
import com.udacity.study.jam.radiotastic.widget.DataImageView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_entity_list)
public class StationListFragment extends Fragment implements StationsPresenter.View, EasyViewHolder.OnItemClickListener {

    private EasyCursorRecyclerAdapter mAdapter;
    private StationsPresenter stationsPresenter;

    @ViewById
    protected RecyclerView recyclerView;
    @ViewById(android.R.id.empty)
    protected DataImageView emptyImageView;
    @ViewById(R.id.refreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @FragmentArg
    protected String categoryId;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        stationsPresenter = new StationsPresenter(this, this);
        stationsPresenter.setCategoryId(categoryId);
        stationsPresenter.initialize();

        initRecyclerView();
        initAdapter();
        initSwipeRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        stationsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        stationsPresenter.pause();
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
        emptyImageView.setImageType(DataImageView.Type.NONE);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
        emptyImageView.setImageType(DataImageView.Type.SYNC);
    }

    @Override
    public void renderStations(Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void showConnectionErrorMessage() {
        SnackBarItem sbi = new SnackBarItem.Builder()
                .setActionClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                    }
                })
                .setMessage("No internet connection!")
                .setActionMessage("Settings")
                .setDuration(5000).build();
        SnackBar.show(getActivity(), sbi);
    }

    @Override
    public void showEmptyCase() {
        swipeRefreshLayout.setRefreshing(false);
        emptyImageView.setImageType(DataImageView.Type.EMPTY);
    }

    @Override
    public boolean isReady() {
        return isAdded();
    }

    @Override
    public boolean isAlreadyLoaded() {
        return mAdapter.getItemCount() > 0;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0)
                                ? 0 : recyclerView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });
    }

    private void initAdapter() {
        mAdapter = new EasyCursorRecyclerAdapter(getActivity(), null);
        mAdapter.bind(StationViewHolder.class);
        mAdapter.setOnClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    private void initSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.gplus_color_1,
                R.color.gplus_color_2,
                R.color.gplus_color_3,
                R.color.gplus_color_4
        );
        swipeRefreshLayout.setOnRefreshListener(stationsPresenter);
    }

    @Override
    public void onItemClick(int position, View view) {
        Cursor cursor = mAdapter.getCursor();
        if (cursor != null) {
            cursor.moveToPosition(position);
            StationCursor stationCursor = new StationCursor(cursor);
            if (getActivity() instanceof Callback) {
                ((Callback) getActivity()).onStationSelected(
                        String.valueOf(stationCursor.getStationId()),
                        stationCursor.getStreamurl());
            }
        }
    }

    public static interface Callback {
        void onStationSelected(String stationID, String streamUrl);
    }

}
