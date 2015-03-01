/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.data.repository.datasource;

import android.content.Context;

import com.udacity.study.jam.radiotastic.data.entity.CategoryEntity;
import com.udacity.study.jam.radiotastic.data.mapper.entity.CategoryEntityDataMapper;
import com.udacity.study.jam.radiotastic.data.network.AppUrlConnectionClient;
import com.udacity.study.jam.radiotastic.data.network.LogableSimpleCallback;
import com.udacity.study.jam.radiotastic.data.network.api.ApiEndpoint;
import com.udacity.study.jam.radiotastic.data.network.api.ApiKey;
import com.udacity.study.jam.radiotastic.data.network.api.DirbleClient;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DirbleCategoryDataSource implements CategoryDataSource {

    private final DirbleClient mApiClient;
    private final Context mContext;
    private final CategoryEntityDataMapper mMapper;

    public DirbleCategoryDataSource(Context context) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(new ApiEndpoint(context))
                .setClient(new AppUrlConnectionClient())
                .build();
        mApiClient = restAdapter.create(DirbleClient.class);
        mContext = context;
        mMapper = new CategoryEntityDataMapper();
    }

    @Override
    public void getCategoriesEntityList(final CategoryListCallback categoryListCallback) {
        mApiClient.listPrimaryCategories(ApiKey.INSTANCE.get(mContext),
                new LogableSimpleCallback<List<CategoryEntity>>() {
                    @Override
                    public void semanticSuccess(List<CategoryEntity> categoryItems,
                                                Response response) {
                        categoryListCallback.onCategoriesEntityListLoaded(
                                mMapper.transform(categoryItems));
                    }
                    @Override
                    public void semanticFailure(RetrofitError error) {
                        categoryListCallback.onError(error);
                    }
                });
    }
}
