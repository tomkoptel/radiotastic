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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.melnykov.fab.FloatingActionButton;
import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.SongItem;
import com.udacity.study.jam.radiotastic.player.PlayerIntentService;
import com.udacity.study.jam.radiotastic.ui.adapter.SongsAdapter;
import com.udacity.study.jam.radiotastic.ui.helper.StationRecyclerHelper;
import com.udacity.study.jam.radiotastic.ui.presenter.StationPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.fragment_detail)
public class StationFragment extends Fragment implements StationPresenter.View {
    private StationPresenter stationPresenter;
    private SongsAdapter mAdapter;

    @ViewById(R.id.recycler)
    protected RecyclerView mRecyclerView;
    @ViewById(R.id.toolbar)
    protected Toolbar mToolbar;
    @ViewById(R.id.fab)
    protected FloatingActionButton mFab;

    @FragmentArg
    protected String stationId;
    @FragmentArg
    protected String streamUrl;

    @InstanceState
    protected boolean mPlaying;

    @Bean
    protected StationRecyclerHelper recyclerHelper;

    @AfterViews
    final void init() {
        ((ActionBarActivity) getActivity()).setSupportActionBar(mToolbar);
        recyclerHelper.init(getView());

        mFab.setImageResource(mPlaying ? R.drawable.ic_av_stop : R.drawable.ic_av_play_arrow);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(false);

        mAdapter = new SongsAdapter(recyclerHelper.getHeaderView());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Click(R.id.fab)
    final void togglePlayBack() {
        if (mPlaying) {
            mPlaying = false;
            mFab.setImageResource(R.drawable.ic_av_play_arrow );
            PlayerIntentService.startActionStop(getActivity());
        } else {
            mPlaying = true;
            mFab.setImageResource(R.drawable.ic_av_stop);
            PlayerIntentService.startActionPlay(getActivity(), streamUrl);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        stationPresenter = new StationPresenter(this, this);
        stationPresenter.setStationId(stationId);
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
    public void renderSongHistory(List<SongItem> songHistory) {
        mAdapter.setDataset(songHistory);
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

}
