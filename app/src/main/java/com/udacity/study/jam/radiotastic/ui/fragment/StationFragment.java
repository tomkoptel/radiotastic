/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.melnykov.fab.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.SongItem;
import com.udacity.study.jam.radiotastic.player.PlayerIntentService;
import com.udacity.study.jam.radiotastic.player.PlayerPref_;
import com.udacity.study.jam.radiotastic.ui.adapter.SongsAdapter;
import com.udacity.study.jam.radiotastic.ui.helper.StationRecyclerHelper;
import com.udacity.study.jam.radiotastic.ui.presenter.StationPresenter;
import com.udacity.study.jam.radiotastic.util.PaletteTransformation;
import com.udacity.study.jam.radiotastic.widget.StateSyncLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

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
    @ViewById(android.R.id.empty)
    protected StateSyncLayout emptyImageView;
    @ViewById(R.id.image)
    protected ImageView stationImageView;

    @FragmentArg
    protected String stationName;
    @FragmentArg
    protected String stationId;
    @FragmentArg
    protected String streamUrl;

    @InstanceState
    protected boolean mPlaying;

    @Bean
    protected StationRecyclerHelper recyclerHelper;

    @Pref
    protected PlayerPref_ playerPref;

    @AfterViews
    final void init() {
        boolean twoPane = (getActivity().findViewById(R.id.detail_content) != null);
        if (!twoPane) {
            ((ActionBarActivity) getActivity()).setSupportActionBar(mToolbar);
        }

        recyclerHelper
                .setup()
                .rootView(getView())
                .title(stationName)
                .init();

        mPlaying = playerPref.stationId().getOr("").equals(stationId);
        mFab.setImageResource(mPlaying ? R.drawable.ic_av_stop : R.drawable.ic_av_play_arrow);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(false);

        mAdapter = new SongsAdapter(recyclerHelper.getHeaderView(), twoPane);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Click(R.id.fab)
    final void togglePlayBack() {
        if (mPlaying) {
            mPlaying = false;
            playerPref.clear();
            mFab.setImageResource(R.drawable.ic_av_play_arrow );
            PlayerIntentService.startActionStop(getActivity());
        } else {
            mPlaying = true;
            playerPref.stationId().put(stationId);
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
        emptyImageView.setImageType(StateSyncLayout.Type.NONE);
    }

    @Override
    public void showLoading() {
        emptyImageView.setImageType(StateSyncLayout.Type.SYNC);
    }

    @Override
    public void renderSongHistory(List<SongItem> songHistory) {
        mAdapter.setDataset(songHistory);
    }

    @Override
    public void renderStationImage(String imageUri) {
        Picasso.with(getActivity())
                .load(imageUri)
                .transform(PaletteTransformation.instance())
                .placeholder(R.drawable.placeholder)
                .into(stationImageView, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) stationImageView.getDrawable()).getBitmap();
                        Palette palette = PaletteTransformation.getPalette(bitmap);
                        int mutedLight = palette.getLightMutedColor(0x000000);
                        stationImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        stationImageView.setBackgroundColor(mutedLight);

                        int vibrantColor = palette.getVibrantColor(0x000000);
                        int vibrantDarkColor = palette.getDarkVibrantColor(0x000000);
                        int vibrantLightColor = palette.getLightVibrantColor(0x000000);
                        mFab.setColorNormal(vibrantColor);
                        mFab.setColorPressed(vibrantDarkColor);
                        mFab.setColorRipple(vibrantLightColor);
                    }
                });
    }

    @Override
    public void showConnectionErrorMessage() {
        emptyImageView.setImageType(StateSyncLayout.Type.ERROR);
    }

    @Override
    public void showEmptyCase() {
        emptyImageView.setImageType(StateSyncLayout.Type.EMPTY);
        emptyImageView.setStateText(R.string.no_songhistory);
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
