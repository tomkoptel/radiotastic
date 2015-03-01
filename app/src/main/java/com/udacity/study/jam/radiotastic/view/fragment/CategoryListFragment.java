/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.view.fragment;

import android.app.Activity;
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

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.UIThread;
import com.udacity.study.jam.radiotastic.data.executor.JobExecutor;
import com.udacity.study.jam.radiotastic.data.repository.CategoryDataRepository;
import com.udacity.study.jam.radiotastic.executor.PostExecutionThread;
import com.udacity.study.jam.radiotastic.executor.ThreadExecutor;
import com.udacity.study.jam.radiotastic.interactor.GetCategoryListUseCase;
import com.udacity.study.jam.radiotastic.interactor.GetCategoryListUseCaseImpl;
import com.udacity.study.jam.radiotastic.model.CategoryModel;
import com.udacity.study.jam.radiotastic.presenter.CategoryListPresenter;
import com.udacity.study.jam.radiotastic.repository.CategoryRepository;
import com.udacity.study.jam.radiotastic.util.SimpleOnItemTouchListener;
import com.udacity.study.jam.radiotastic.view.CategoryListView;
import com.udacity.study.jam.radiotastic.view.adapter.CategoryAdapter;

import java.util.Collection;

import timber.log.Timber;

public class CategoryListFragment extends Fragment implements CategoryListView {
    private static final String LOG_TAG = CategoryListFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private GestureDetectorCompat gestureDetectorCompat;
    private CategoryAdapter mAdapter;
    private CategoryListPresenter categoryListPresenter;
    private Callback categorySelectedtListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Callback) {
            this.categorySelectedtListener = (Callback) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.tag(LOG_TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category_list, container, false);
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

        mAdapter = new CategoryAdapter();
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new ItemTouchListener());

        gestureDetectorCompat = new GestureDetectorCompat(getActivity(),
                new RecyclerViewGestureListener());


        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        CategoryRepository categoryRepository = new CategoryDataRepository();

        GetCategoryListUseCase getCategoryListUseCase =
                new GetCategoryListUseCaseImpl(categoryRepository,
                        threadExecutor, postExecutionThread);
        categoryListPresenter = new CategoryListPresenter(this, getCategoryListUseCase);
        categoryListPresenter.init();
    }

    @Override
    public void onResume() {
        super.onResume();
        categoryListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        categoryListPresenter.pause();
    }

    @Override
    public void renderCategoryList(Collection<CategoryModel> categoryItems) {
        mAdapter.setDataset(categoryItems);
    }

    @Override
    public void viewUser(CategoryModel categoryModel) {
        categorySelectedtListener.onCategorySelected(categoryModel.getId());
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

            CategoryModel categoryModel = mAdapter.getItem(position);
            categoryListPresenter.onCategoryClicked(categoryModel);

            return super.onSingleTapConfirmed(event);
        }
    }

    public static interface Callback {
        void onCategorySelected(long categoryID);
    }
}
