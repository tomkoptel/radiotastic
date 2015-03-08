/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.category;

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

import com.udacity.study.jam.radiotastic.ApplicationComponent;
import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.domain.ImmediateSyncCase;
import com.udacity.study.jam.radiotastic.domain.ObserveSyncStateCase;
import com.udacity.study.jam.radiotastic.util.SimpleOnItemTouchListener;

import javax.inject.Inject;

public class CategoryListFragment extends Fragment {
    private static final String LOG_TAG = CategoryListFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private GestureDetectorCompat gestureDetectorCompat;
    private Toast mToast;

    @Inject
    ImmediateSyncCase immediateSync;
    @Inject
    ObserveSyncStateCase observeAccountCase;

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
        ApplicationComponent.Initializer.init(getActivity()).inject(this);

        immediateSync.start(new Bundle());

        mToast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        observeAccountCase.create(new ObserveSyncStateCase.SyncStatusCallBack() {
            @Override
            public void onStatusChanged(boolean syncIsActive) {
                mToast.setText("Sync is: " + syncIsActive);
                mToast.show();
            }
        });

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

        gestureDetectorCompat = new GestureDetectorCompat(getActivity(),
                new RecyclerViewGestureListener());
    }

    @Override
    public void onPause() {
        super.onPause();
        observeAccountCase.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        observeAccountCase.resume();
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
            Toast.makeText(getActivity(), "Selected " + position, Toast.LENGTH_SHORT).show();
//            CategoryItem categoryItem = mAdapter.getItem(position);
//            if (getActivity() instanceof Callback) {
//                ((Callback) getActivity()).onCategorySelected(categoryItem.getId());
//            }
            return super.onSingleTapConfirmed(event);
        }
    }

    public static interface Callback {
        void onCategorySelected(double categoryID);
    }
}
