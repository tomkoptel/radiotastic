package com.udacity.study.jam.radiotastic.db.category;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.study.jam.radiotastic.db.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code category} table.
 */
public class CategoryContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return CategoryColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable CategorySelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Represents id of object which resides on backend.
     */
    public CategoryContentValues putCategoryId(long value) {
        mContentValues.put(CategoryColumns.CATEGORY_ID, value);
        return this;
    }


    /**
     * Name of category
     */
    public CategoryContentValues putName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("name must not be null");
        mContentValues.put(CategoryColumns.NAME, value);
        return this;
    }


    public CategoryContentValues putDescription(@Nullable String value) {
        mContentValues.put(CategoryColumns.DESCRIPTION, value);
        return this;
    }

    public CategoryContentValues putDescriptionNull() {
        mContentValues.putNull(CategoryColumns.DESCRIPTION);
        return this;
    }
}
