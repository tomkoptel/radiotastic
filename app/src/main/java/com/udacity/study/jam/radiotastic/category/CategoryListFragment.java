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

import com.udacity.study.jam.radiotastic.CategoryItem;
import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.api.ApiEndpoint;
import com.udacity.study.jam.radiotastic.api.ApiKey;
import com.udacity.study.jam.radiotastic.api.DirbleClient;
import com.udacity.study.jam.radiotastic.network.AppUrlConnectionClient;
import com.udacity.study.jam.radiotastic.network.LogableSimpleCallback;
import com.udacity.study.jam.radiotastic.util.SimpleOnItemTouchListener;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.client.Response;
import timber.log.Timber;

public class CategoryListFragment extends Fragment {
            private static final String LOG_TAG = CategoryListFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private GestureDetectorCompat gestureDetectorCompat;
    private CategoryAdapter mAdapter;

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
    }

    @Override
    public void onStart() {
        super.onStart();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(new ApiEndpoint(getActivity()))
                .setClient(new AppUrlConnectionClient())
                .build();
        DirbleClient client = restAdapter.create(DirbleClient.class);
        Timber.i("Requesting primary categories");
        client.listPrimaryCategories(ApiKey.INSTANCE.get(getActivity()),
                new LogableSimpleCallback<List<CategoryItem>>() {
                    @Override
                    public void semanticSuccess(List<CategoryItem> categoryItems, Response response) {
                        mAdapter.setDataset(categoryItems);
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
            Toast.makeText(getActivity(), "Selected " + position, Toast.LENGTH_SHORT).show();
            return super.onSingleTapConfirmed(event);
        }
    }
}
