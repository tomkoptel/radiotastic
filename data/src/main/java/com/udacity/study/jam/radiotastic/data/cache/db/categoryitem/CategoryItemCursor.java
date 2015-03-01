package com.udacity.study.jam.radiotastic.data.cache.db.categoryitem;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.study.jam.radiotastic.data.cache.db.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code category_item} table.
 */
public class CategoryItemCursor extends AbstractCursor implements CategoryItemModel {
    public CategoryItemCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(CategoryItemColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Represents id of object which resides on backend.
     */
    public long getCategoryId() {
        Long res = getLongOrNull(CategoryItemColumns.CATEGORY_ID);
        if (res == null)
            throw new NullPointerException("The value of 'category_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Name of category
     * Cannot be {@code null}.
     */
    @NonNull
    public String getName() {
        String res = getStringOrNull(CategoryItemColumns.NAME);
        if (res == null)
            throw new NullPointerException("The value of 'name' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getDescription() {
        String res = getStringOrNull(CategoryItemColumns.DESCRIPTION);
        return res;
    }
}
