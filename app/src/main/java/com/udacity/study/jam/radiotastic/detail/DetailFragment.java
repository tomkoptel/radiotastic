/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.data.entity.StationEntityData;
import com.udacity.study.jam.radiotastic.data.network.AppUrlConnectionClient;
import com.udacity.study.jam.radiotastic.data.network.LogableSimpleCallback;
import com.udacity.study.jam.radiotastic.data.network.api.ApiEndpoint;
import com.udacity.study.jam.radiotastic.data.network.api.ApiKey;
import com.udacity.study.jam.radiotastic.data.network.api.DirbleClient;
import com.udacity.study.jam.radiotastic.player.PlayerIntentService;

import retrofit.RestAdapter;
import retrofit.client.Response;
import timber.log.Timber;

public class DetailFragment extends Fragment {
    private static final String STATION_ID_ARG = "stationID";

    private TextView mName;
    private TextView mDescription;
    private int mStationId;
    public String mStreamUrl;

    public static DetailFragment init(int stationId) {
        Bundle args = new Bundle();
        args.putInt(STATION_ID_ARG, stationId);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);
        return detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null && args.containsKey(STATION_ID_ARG)) {
            mStationId = args.getInt(STATION_ID_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        mName = (TextView) root.findViewById(R.id.name);
        mDescription = (TextView) root.findViewById(R.id.description);
        root.findViewById(R.id.playAction)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStreamUrl != null) {
                    PlayerIntentService.startActionPlay(getActivity(), mStreamUrl);
                }
            }
        });
        root.findViewById(R.id.stopAction)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerIntentService.startActionStop(getActivity());
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(new ApiEndpoint(getActivity()))
                .setClient(new AppUrlConnectionClient())
                .build();
        DirbleClient client = restAdapter.create(DirbleClient.class);
        Timber.i("Requesting station data for id: " + mStationId);
        client.getStationData(
                ApiKey.INSTANCE.get(getActivity()),
                mStationId,
                new LogableSimpleCallback<StationEntityData>() {
                    @Override
                    public void semanticSuccess(StationEntityData data, Response response) {
                        mName.setText(data.getName());
                        mDescription.setText(data.getDescription());
                        mStreamUrl = data.getStreamurl();
                    }
                });
    }
}
