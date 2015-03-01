/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.data.repository.datasource;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.udacity.study.jam.radiotastic.Category;
import com.udacity.study.jam.radiotastic.data.cache.db.categoryitem.CategoryItemColumns;
import com.udacity.study.jam.radiotastic.data.cache.db.categoryitem.CategoryItemCursor;
import com.udacity.study.jam.radiotastic.data.mapper.cursor.CategoryCursorDataMapper;

import java.util.ArrayList;
import java.util.List;

public class DbCategoryDataSource implements CategoryDataSource,
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CATEGORY_LIST_LOADER = 100;
    private final Activity mActivity;
    private final CategoryCursorDataMapper mMapper;
    private CategoryListCallback mCallback;

    public DbCategoryDataSource(Activity activity) {
        mActivity = activity;
        mMapper = new CategoryCursorDataMapper();
    }

    @Override
    public void getCategoriesEntityList(CategoryListCallback categoryListCallback) {
        mCallback = categoryListCallback;
        mActivity.getLoaderManager().initLoader(CATEGORY_LIST_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mActivity, CategoryItemColumns.CONTENT_URI,
                CategoryItemColumns.ALL_COLUMNS, null, null,
                CategoryItemColumns.NAME + " ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        CategoryItemCursor categoryItemCursor;
        Category category;
        List<Category> categoryList = new ArrayList<Category>(20);
        while (data.moveToNext()) {
            categoryItemCursor = new CategoryItemCursor(data);
            category = mMapper.transform(categoryItemCursor);
            categoryList.add(category);
        }
        mCallback.onCategoriesEntityListLoaded(categoryList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
