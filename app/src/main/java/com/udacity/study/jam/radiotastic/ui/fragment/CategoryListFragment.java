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
import android.view.View;

import com.kenny.snackbar.SnackBar;
import com.kenny.snackbar.SnackBarItem;
import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.db.category.CategoryCursor;
import com.udacity.study.jam.radiotastic.ui.adapter.easy.EasyCursorRecyclerAdapter;
import com.udacity.study.jam.radiotastic.ui.adapter.easy.EasyViewHolder;
import com.udacity.study.jam.radiotastic.ui.adapter.holder.CategoryViewHolder;
import com.udacity.study.jam.radiotastic.ui.presenter.CategoriesPresenter;
import com.udacity.study.jam.radiotastic.widget.StateSyncLayout;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_entity_list)
public class CategoryListFragment extends Fragment implements CategoriesPresenter.View, EasyViewHolder.OnItemClickListener {

    private CategoriesPresenter categoriesPresenter;
    private LinearLayoutManager linearLayoutManager;
    private EasyCursorRecyclerAdapter mAdapter;

    @ViewById
    protected RecyclerView recyclerView;
    @ViewById(android.R.id.empty)
    protected StateSyncLayout emptyImageView;
    @ViewById(R.id.refreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @InstanceState
    protected int mPosition = -1;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        categoriesPresenter = new CategoriesPresenter(this, this);
        categoriesPresenter.initialize();

        initRecyclerView();
        initAdapter();
        initSwipeRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ActionBarActivity) getActivity())
                .getSupportActionBar().setTitle(R.string.main_categories);
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
        emptyImageView.setImageType(StateSyncLayout.Type.NONE);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
        emptyImageView.setImageType(StateSyncLayout.Type.SYNC);
    }

    @Override
    public void renderCategories(Cursor data) {
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
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(getResources().getColor(R.color.divider))
                        .margin(getResources().getDimensionPixelSize(R.dimen.list_item_left_padding),
                                getResources().getDimensionPixelSize(R.dimen.list_item_right_padding))
                        .build());
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
        mAdapter = new EasyCursorRecyclerAdapter();
        mAdapter.bind(CategoryViewHolder.class);
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
        swipeRefreshLayout.setOnRefreshListener(categoriesPresenter);
    }

    @Override
    public void onItemClick(int position, View view) {
        Cursor cursor = mAdapter.getCursor();
        if (cursor != null) {
            updateSelection(position);

            cursor.moveToPosition(position);
            CategoryCursor categoryCursor = new CategoryCursor(cursor);
            if (getActivity() instanceof Callback) {
                ((Callback) getActivity()).onCategorySelected(
                        String.valueOf(categoryCursor.getCategoryId()),
                        categoryCursor.getName());
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

    public interface Callback {
        void onCategorySelected(String categoryID, String categoryName);
    }
}
