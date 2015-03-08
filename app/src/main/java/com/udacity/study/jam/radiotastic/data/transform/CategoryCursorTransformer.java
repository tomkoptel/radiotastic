/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.data.transform;

import android.database.Cursor;

import com.udacity.study.jam.radiotastic.CategoryItem;
import com.udacity.study.jam.radiotastic.db.categoryitem.CategoryItemCursor;

import java.util.ArrayList;
import java.util.Collection;

public class CategoryCursorTransformer {
    public Collection<CategoryItem> transform(Cursor cursor) {
        CategoryItemCursor categoryItemCursor;
        CategoryItem categoryItem;

        Collection<CategoryItem> collection = new ArrayList<CategoryItem>(cursor.getCount());
        while (cursor.moveToNext()) {
            categoryItemCursor = new CategoryItemCursor(cursor);
            categoryItem = transform(categoryItemCursor);
            collection.add(categoryItem);
        }

        return collection;
    }

    public CategoryItem transform(CategoryItemCursor categoryItemCursor) {
        return new CategoryItem()
                .withId(categoryItemCursor.getCategoryId())
                .withDescription(categoryItemCursor.getDescription())
                .withName(categoryItemCursor.getName());
    }
}
