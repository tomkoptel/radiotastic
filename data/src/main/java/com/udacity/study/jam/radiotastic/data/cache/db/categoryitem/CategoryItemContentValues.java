/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.data.cache.db.categoryitem;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.study.jam.radiotastic.data.cache.db.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code category_item} table.
 */
public class CategoryItemContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return CategoryItemColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable CategoryItemSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Represents id of object which resides on backend.
     */
    public CategoryItemContentValues putCategoryId(long value) {
        mContentValues.put(CategoryItemColumns.CATEGORY_ID, value);
        return this;
    }


    /**
     * Name of category
     */
    public CategoryItemContentValues putName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("name must not be null");
        mContentValues.put(CategoryItemColumns.NAME, value);
        return this;
    }


    public CategoryItemContentValues putDescription(@Nullable String value) {
        mContentValues.put(CategoryItemColumns.DESCRIPTION, value);
        return this;
    }

    public CategoryItemContentValues putDescriptionNull() {
        mContentValues.putNull(CategoryItemColumns.DESCRIPTION);
        return this;
    }
}
