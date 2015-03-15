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
import com.udacity.study.jam.radiotastic.db.category.CategoryCursor;
import com.udacity.study.jam.radiotastic.ui.adapter.CategoryAdapter;
import com.udacity.study.jam.radiotastic.ui.presenter.CategoriesPresenter;
import com.udacity.study.jam.radiotastic.util.SimpleOnItemTouchListener;
import com.udacity.study.jam.radiotastic.widget.DataImageView;

public class CategoryListFragment extends Fragment implements CategoriesPresenter.View {

    private RecyclerView recyclerView;
    private GestureDetectorCompat gestureDetectorCompat;
    private CategoryAdapter mAdapter;
    private DataImageView emptyImageView;

    private SwipeRefreshLayout swipeRefreshLayout;
    private CategoriesPresenter categoriesPresenter;

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

        categoriesPresenter = new CategoriesPresenter(this, this);
        categoriesPresenter.initialize();

        initRecyclerView();
        initSwipeRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        categoriesPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        categoriesPresenter.pause();
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
    public void renderCategories(Cursor data) {
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

        mAdapter = new CategoryAdapter(getActivity(), null);
        recyclerView.setAdapter(mAdapter);
    }

    private void initSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.holo_blue_dark,
                R.color.holo_yellow_dark,
                R.color.holo_green_dark,
                R.color.holo_purple_dark,
                R.color.holo_red_dark
        );
        swipeRefreshLayout.setOnRefreshListener(categoriesPresenter);
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
                CategoryCursor categoryCursor = new CategoryCursor(cursor);
                if (getActivity() instanceof Callback) {
                    ((Callback) getActivity()).onCategorySelected(String.valueOf(categoryCursor.getCategoryId()));
                }
            }

            return super.onSingleTapConfirmed(event);
        }
    }

    public static interface Callback {
        void onCategorySelected(String categoryID);
    }
}
