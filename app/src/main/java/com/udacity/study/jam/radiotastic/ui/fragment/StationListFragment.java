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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.kenny.snackbar.SnackBar;
import com.kenny.snackbar.SnackBarItem;
import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.db.station.StationCursor;
import com.udacity.study.jam.radiotastic.ui.UiPref_;
import com.udacity.study.jam.radiotastic.ui.adapter.easy.EasyCursorRecyclerAdapter;
import com.udacity.study.jam.radiotastic.ui.adapter.easy.EasyViewHolder;
import com.udacity.study.jam.radiotastic.ui.adapter.holder.StationViewHolder;
import com.udacity.study.jam.radiotastic.ui.dialog.SortDialogFragment;
import com.udacity.study.jam.radiotastic.ui.presenter.StationsPresenter;
import com.udacity.study.jam.radiotastic.widget.StateSyncLayout;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

@OptionsMenu(R.menu.station_page_menu)
@EFragment(R.layout.fragment_entity_list)
public class StationListFragment extends Fragment
        implements StationsPresenter.View, EasyViewHolder.OnItemClickListener {

    private EasyCursorRecyclerAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private StationsPresenter stationsPresenter;
    protected boolean isMenuVisible;

    @ViewById
    protected RecyclerView recyclerView;
    @ViewById(android.R.id.empty)
    protected StateSyncLayout emptyImageView;
    @ViewById(R.id.refreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @FragmentArg
    protected String categoryId;
    @FragmentArg
    protected String categoryName;

    @OptionsMenuItem(R.id.sortOrder)
    protected MenuItem sortOrderMenuItem;
    @OptionsMenuItem(R.id.sortOption)
    protected MenuItem sortOptionMenuItem;

    @Pref
    protected UiPref_ uiPref;

    @InstanceState
    protected int mPosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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

    @OptionsItem(R.id.sortOption)
    final void sortStations() {
        SortDialogFragment.show(getFragmentManager());
    }

    @OptionsItem(R.id.sortOrder)
    final void sortOrderStations() {
        String sortOrder = uiPref.sortOrder().get();
        boolean isAsc = "asc".equals(sortOrder);
        uiPref.sortOrder().put(isAsc ? "desc" : "asc");
        toggleOrderIcon();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        sortOptionMenuItem.setVisible(isMenuVisible);
        sortOrderMenuItem.setVisible(isMenuVisible);
        toggleOrderIcon();
    }

    private void toggleOrderIcon() {
        String sortOrder = uiPref.sortOrder().get();
        boolean isAsc = "asc".equals(sortOrder);
        int resIcon = isAsc ? R.drawable.ic_sort_descending_white_36dp :
                R.drawable.ic_sort_ascending_white_36dp;
        int resTitle = isAsc ? R.string.sort_desc : R.string.sort_asc;
        sortOrderMenuItem.setIcon(resIcon);
        sortOrderMenuItem.setTitle(resTitle);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(categoryName);
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
        emptyImageView.setImageType(StateSyncLayout.Type.NONE);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
        emptyImageView.setImageType(StateSyncLayout.Type.SYNC);
    }

    @Override
    public void renderStations(Cursor data) {
        isMenuVisible = true;
        getActivity().supportInvalidateOptionsMenu();
        mAdapter.swapCursor(data);
        linearLayoutManager.scrollToPosition(mPosition != -1 ? mPosition : 0);
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
        emptyImageView.setImageType(StateSyncLayout.Type.EMPTY);
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
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(getResources().getColor(R.color.divider))
                        .margin(getResources().getDimensionPixelSize(R.dimen.list_item_left_padding),
                                getResources().getDimensionPixelSize(R.dimen.list_item_right_padding))
                        .build());
        recyclerView.setHasFixedSize(true);
        recyclerView.setOnScrollListener(new ScrollListener());
    }

    private void initAdapter() {
        mAdapter = new EasyCursorRecyclerAdapter();
        mAdapter.bind(StationViewHolder.class);
        mAdapter.setOnClickListener(this);
        recyclerView.setAdapter(mAdapter);

        if (mPosition != -1) {
            mAdapter.setSelected(mPosition);
        }
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
            updateSelection(position);
            cursor.moveToPosition(position);
            StationCursor stationCursor = new StationCursor(cursor);
            if (getActivity() instanceof Callback) {
                ((Callback) getActivity()).onStationSelected(
                        String.valueOf(stationCursor.getStationId()),
                        stationCursor.getName(),
                        stationCursor.getStreamurl());
            }
        }
    }

    private void updateSelection(int position) {
        if (mPosition != -1) {
            mAdapter.clearSelection(mPosition);
        }
        mPosition = position;
        mAdapter.setSelected(mPosition);
    }

    @Override
    public void onDestroyView() {
        mAdapter.swapCursor(null);
        mAdapter.setOnClickListener(null);
        recyclerView.setOnScrollListener(null);
        super.onDestroyView();
    }

    public interface Callback {
        void onStationSelected(String stationID, String stationName, String streamUrl);
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int topRowVerticalPosition =
                    (recyclerView == null || recyclerView.getChildCount() == 0)
                            ? 0 : recyclerView.getChildAt(0).getTop();
            swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
        }
    }

}
