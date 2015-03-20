/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.fragment;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.SongItem;
import com.udacity.study.jam.radiotastic.StationDetails;
import com.udacity.study.jam.radiotastic.ui.adapter.SongsAdapter;
import com.udacity.study.jam.radiotastic.ui.presenter.StationPresenter;
import com.udacity.study.jam.radiotastic.util.SimpleOnItemTouchListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DimensionPixelSizeRes;

@EFragment(R.layout.fragment_detail)
public class StationFragment extends Fragment implements StationPresenter.View, ObservableScrollViewCallbacks {
    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    private StationPresenter stationPresenter;
    private GestureDetectorCompat gestureDetectorCompat;
    private SongsAdapter mAdapter;

    @ViewById(R.id.recycler)
    protected ObservableRecyclerView mRecyclerView;
    @ViewById(R.id.toolbar)
    protected Toolbar mToolbar;
    @ViewById(R.id.title)
    protected TextView mTitleView;
    @ViewById(R.id.image)
    protected View mImageView;
    @ViewById(R.id.overlay)
    protected View mOverlayView;
    @ViewById(R.id.list_background)
    protected View mRecyclerViewBackground;
    @ViewById(R.id.fab)
    protected View mFab;

    @FragmentArg
    protected String stationId;
    @FragmentArg
    protected String streamUrl;

    @DimensionPixelSizeRes(R.dimen.flexible_space_image_height)
    protected int mFlexibleSpaceImageHeight;
    @DimensionPixelSizeRes(R.dimen.flexible_space_show_fab_offset)
    protected int mFlexibleSpaceShowFabOffset;
    @DimensionPixelSizeRes(R.dimen.margin_standard)
    protected int mFabMargin;

    private int mActionBarSize;
    private boolean mFabIsShown;

    @AfterViews
    final void init() {
        ((ActionBarActivity) getActivity()).setSupportActionBar(mToolbar);
        mActionBarSize = getActionBarSize();

        mRecyclerView.setScrollViewCallbacks(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.addOnItemTouchListener(new ItemTouchListener());

        gestureDetectorCompat = new GestureDetectorCompat(getActivity(),
                new RecyclerViewGestureListener());

        final View headerView = LayoutInflater.from(getActivity())
                .inflate(R.layout.recycler_header, null);
        headerView.post(new Runnable() {
            @Override
            public void run() {
                headerView.getLayoutParams().height = mFlexibleSpaceImageHeight;
            }
        });

        mAdapter = new SongsAdapter(headerView);
        mRecyclerView.setAdapter(mAdapter);

        mToolbar.setBackgroundColor(Color.TRANSPARENT);

        mTitleView.setText(getActivity().getTitle());
        getActivity().setTitle(null);

        ViewHelper.setScaleX(mFab, 0);
        ViewHelper.setScaleY(mFab, 0);

        mOverlayView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Ensure you call it only once :
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    mOverlayView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    mOverlayView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                // Translate FAB
                float fabTranslationY = translateFab(0);
                // Show/hide FAB
                toggleFab(fabTranslationY);
            }
        });

        final View contentView = getActivity().getWindow().getDecorView()
                .findViewById(android.R.id.content);
        contentView.post(new Runnable() {
            @Override
            public void run() {
                // mRecylcerViewBackground's should fill its parent vertically
                // but the height of the content view is 0 on 'onCreate'.
                // So we should get it with post().
                mRecyclerViewBackground.getLayoutParams().height = contentView.getHeight();
            }
        });

        //since you cannot programatically add a headerview to a recyclerview we added an empty view as the header
        // in the adapter and then are shifting the views OnCreateView to compensate
        final float scale = 1 + MAX_TEXT_SCALE_DELTA;
        mRecyclerViewBackground.post(new Runnable() {
            @Override
            public void run() {
                ViewHelper.setTranslationY(mRecyclerViewBackground, mFlexibleSpaceImageHeight);
            }
        });
        ViewHelper.setTranslationY(mOverlayView, mFlexibleSpaceImageHeight);
        mTitleView.post(new Runnable() {
            @Override
            public void run() {
                ViewHelper.setTranslationY(mTitleView, (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale));
                ViewHelper.setPivotX(mTitleView, 0);
                ViewHelper.setPivotY(mTitleView, 0);
                ViewHelper.setScaleX(mTitleView, scale);
                ViewHelper.setScaleY(mTitleView, scale);
            }
        });
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Translate list background
        ViewHelper.setTranslationY(mRecyclerViewBackground, Math.max(0, -scrollY + mFlexibleSpaceImageHeight));

        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        setPivotXToTitle();
        ViewHelper.setPivotY(mTitleView, 0);
        ViewHelper.setScaleX(mTitleView, scale);
        ViewHelper.setScaleY(mTitleView, scale);

        // Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);

        // Translate FAB
        float fabTranslationY = translateFab(scrollY);
        // Show/hide FAB
        toggleFab(fabTranslationY);

        if (scrollY < mFlexibleSpaceImageHeight) {
            ViewHelper.setTranslationY(mToolbar, 0);
        } else {
            ViewHelper.setTranslationY(mToolbar, -scrollY);
        }
    }

    private float translateFab(int scrollY) {
        int maxFabTranslationY = mFlexibleSpaceImageHeight - mFab.getHeight() / 2;
        float fabTranslationY = ScrollUtils.getFloat(
                -scrollY + mFlexibleSpaceImageHeight - mFab.getHeight() / 2,
                mActionBarSize - mFab.getHeight() / 2,
                maxFabTranslationY);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
            // which causes FAB's OnClickListener not working.
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFab.getLayoutParams();
            lp.leftMargin = mOverlayView.getWidth() - mFabMargin - mFab.getWidth();
            lp.topMargin = (int) fabTranslationY;
            mFab.requestLayout();
        } else {
            ViewHelper.setTranslationX(mFab, mOverlayView.getWidth() - mFabMargin - mFab.getWidth());
            ViewHelper.setTranslationY(mFab, fabTranslationY);
        }
        return fabTranslationY;
    }

    private void toggleFab(float fabTranslationY) {
        if (fabTranslationY < mFlexibleSpaceShowFabOffset) {
            hideFab();
        } else {
            showFab();
        }
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = getActivity().obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPivotXToTitle() {
        Configuration config = getResources().getConfiguration();
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
                && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            ViewHelper.setPivotX(mTitleView, getActivity().findViewById(android.R.id.content).getWidth());
        } else {
            ViewHelper.setPivotX(mTitleView, 0);
        }
    }

    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
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

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

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
            View view = mRecyclerView.findChildViewUnder(event.getX(), event.getY());
            int position = mRecyclerView.getChildPosition(view);
            SongItem item = mAdapter.getItem(position);
            if (item != null) {

            }
            return super.onSingleTapConfirmed(event);
        }
    }
}
