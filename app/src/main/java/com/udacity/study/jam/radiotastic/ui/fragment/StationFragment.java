/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.SongItem;
import com.udacity.study.jam.radiotastic.StationDetails;
import com.udacity.study.jam.radiotastic.player.PlayerIntentService;
import com.udacity.study.jam.radiotastic.ui.adapter.SongsAdapter;
import com.udacity.study.jam.radiotastic.ui.presenter.StationPresenter;
import com.udacity.study.jam.radiotastic.util.SimpleOnItemTouchListener;

import timber.log.Timber;

public class StationFragment extends Fragment implements StationPresenter.View {
    private static final String STATION_ID_ARG = "station_id";
    private static final String STATION_STREAM_URL_ARG = "station_stream_url";

    private TextView mName;
    private TextView mDescription;
    private String mStationId;
    private String mStreamUrl;
    private StationPresenter stationPresenter;
    private RecyclerView recyclerView;
    private GestureDetectorCompat gestureDetectorCompat;
    private SongsAdapter mAdapter;

    public static StationFragment init(String stationId, String streamUrl) {
        Bundle args = new Bundle();
        args.putString(STATION_ID_ARG, stationId);
        args.putString(STATION_STREAM_URL_ARG, streamUrl);
        StationFragment stationFragment = new StationFragment();
        stationFragment.setArguments(args);
        return stationFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.tag(StationFragment.class.getSimpleName());
        Bundle args = getArguments();
        if (args != null && args.containsKey(STATION_ID_ARG)) {
            mStationId = args.getString(STATION_ID_ARG);
            mStreamUrl = args.getString(STATION_STREAM_URL_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        mDescription = (TextView) root.findViewById(R.id.description);
        mDescription = (TextView) root.findViewById(R.id.description);
        root.findViewById(R.id.playAction)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlayerIntentService.startActionPlay(getActivity(), mStreamUrl);
                    }
                });
        root.findViewById(R.id.stopAction)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlayerIntentService.startActionStop(getActivity());
                    }
                });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(new ItemTouchListener());

        gestureDetectorCompat = new GestureDetectorCompat(getActivity(),
                new RecyclerViewGestureListener());

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new SongsAdapter();
        recyclerView.setAdapter(mAdapter);

        stationPresenter = new StationPresenter(this, this);
        stationPresenter.setStationId(mStationId);
        stationPresenter.initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        stationPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        stationPresenter.pause();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void renderStation(StationDetails stationDetails) {
        mAdapter.setDataset(stationDetails.getSonghistory());
    }

    @Override
    public void showConnectionErrorMessage() {

    }

    @Override
    public void showEmptyCase() {

    }

    @Override
    public boolean isReady() {
        return isAdded();
    }

    @Override
    public boolean isAlreadyLoaded() {
        return false;
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
            SongItem item = mAdapter.getItem(position);
            if (item != null) {

            }
            return super.onSingleTapConfirmed(event);
        }
    }
}
