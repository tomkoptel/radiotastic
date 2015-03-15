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
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.kenny.snackbar.SnackBar;
import com.kenny.snackbar.SnackBarItem;
import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.db.station.StationCursor;
import com.udacity.study.jam.radiotastic.ui.adapter.StationsAdapter;
import com.udacity.study.jam.radiotastic.ui.presenter.StationsPresenter;
import com.udacity.study.jam.radiotastic.util.SimpleOnItemTouchListener;
import com.udacity.study.jam.radiotastic.widget.DataImageView;

public class StationListFragment extends Fragment implements StationsPresenter.View {
    private static final String CATEGORY_ID_ARG = "categoryID";

    private RecyclerView recyclerView;
    private DataImageView emptyImageView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private GestureDetectorCompat gestureDetectorCompat;
    private StationsAdapter mAdapter;

    private StationsPresenter stationsPresenter;
    private String mCategoryId;

    public static StationListFragment init(String categoryID) {
        Bundle args = new Bundle();
        args.putString(CATEGORY_ID_ARG, categoryID);
        StationListFragment stationListFragment = new StationListFragment();
        stationListFragment.setArguments(args);
        return stationListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null && args.containsKey(CATEGORY_ID_ARG)) {
            mCategoryId = args.getString(CATEGORY_ID_ARG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_entity_list, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        emptyImageView = (DataImageView) root.findViewById(android.R.id.empty);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refreshLayout);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        stationsPresenter = new StationsPresenter(this, this);
        stationsPresenter.setCategoryId(mCategoryId);
        stationsPresenter.initialize();

        initRecyclerView();
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
        // actually VERTICAL is the default,
        // just remember: LinearLayoutManager
        // supports HORIZONTAL layout out of the box
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // you can set the first visible item like this:
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);

        // you can set the first visible item like this:
        recyclerView.setHasFixedSize(true);

        mAdapter = new StationsAdapter(getActivity(), null);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new ItemTouchListener());

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0)
                                ? 0 : recyclerView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        gestureDetectorCompat = new GestureDetectorCompat(getActivity(),
                new RecyclerViewGestureListener());
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

    private class ItemTouchListener extends SimpleOnItemTouchListener {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            gestureDetectorCompat.onTouchEvent(motionEvent);
            return false;
        }
    }

    private class RecyclerViewGestureListener
            extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            View view = recyclerView.findChildViewUnder(event.getX(), event.getY());
            int position = recyclerView.getChildPosition(view);
            Cursor cursor = mAdapter.getCursor();
            if (cursor != null) {
                cursor.moveToPosition(position);
                StationCursor stationCursor = new StationCursor(cursor);
                if (getActivity() instanceof Callback) {
                    ((Callback) getActivity()).onStationSelected(String.valueOf(stationCursor.getStationId()), stationCursor.getStreamurl());
                }
            }
            return super.onSingleTapConfirmed(event);
        }
    }

    public static interface Callback {
        void onStationSelected(String stationID, String streamUrl);
    }

}
