/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.view.fragment;

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
import android.widget.Toast;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.data.entity.StationEntityItem;
import com.udacity.study.jam.radiotastic.data.network.AppUrlConnectionClient;
import com.udacity.study.jam.radiotastic.data.network.LogableSimpleCallback;
import com.udacity.study.jam.radiotastic.data.network.api.ApiEndpoint;
import com.udacity.study.jam.radiotastic.data.network.api.ApiKey;
import com.udacity.study.jam.radiotastic.data.network.api.DirbleClient;
import com.udacity.study.jam.radiotastic.util.SimpleOnItemTouchListener;
import com.udacity.study.jam.radiotastic.view.adapter.StationAdapter;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.client.Response;
import timber.log.Timber;

public class StationListFragment extends Fragment {
    private static final String CATEGORY_ID_ARG = "categoryID";

    private RecyclerView recyclerView;
    private GestureDetectorCompat gestureDetectorCompat;
    private StationAdapter mAdapter;
    private int mCategoryId;

    public static StationListFragment init(long categoryID) {
        Bundle args = new Bundle();
        args.putLong(CATEGORY_ID_ARG, categoryID);
        StationListFragment stationListFragment = new StationListFragment();
        stationListFragment.setArguments(args);
        return stationListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null && args.containsKey(CATEGORY_ID_ARG)) {
            mCategoryId = args.getInt(CATEGORY_ID_ARG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_station_list, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

        mAdapter = new StationAdapter();
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new ItemTouchListener());

        gestureDetectorCompat = new GestureDetectorCompat(getActivity(),
                new RecyclerViewGestureListener());
    }

    @Override
    public void onStart() {
        super.onStart();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(new ApiEndpoint(getActivity()))
                .setClient(new AppUrlConnectionClient())
                .build();
        DirbleClient client = restAdapter.create(DirbleClient.class);
        Timber.i("Requesting stations for category: " + mCategoryId);
        client.listStations(
                ApiKey.INSTANCE.get(getActivity()),
                mCategoryId,
                new LogableSimpleCallback<List<StationEntityItem>>() {
                    @Override
                    public void semanticSuccess(List<StationEntityItem> stationItems, Response response) {
                        mAdapter.setDataset(stationItems);
                    }
                });
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
            StationEntityItem stationItem = mAdapter.getItem(position);
            if (getActivity() instanceof Callback) {
                ((Callback) getActivity()).onStationSelected(stationItem.getId());
            }
            Toast.makeText(getActivity(), "Selected " + position, Toast.LENGTH_SHORT).show();
            return super.onSingleTapConfirmed(event);
        }
    }

    public static interface Callback {
        void onStationSelected(int stationID);
    }

}
