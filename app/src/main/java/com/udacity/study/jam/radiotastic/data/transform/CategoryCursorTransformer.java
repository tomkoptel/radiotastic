/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.data.transform;

import com.udacity.study.jam.radiotastic.CategoryItem;
import com.udacity.study.jam.radiotastic.db.category.CategoryCursor;

public class CategoryCursorTransformer {
    public CategoryItem transform(CategoryCursor categoryItemCursor) {
        return new CategoryItem()
                .withId(categoryItemCursor.getCategoryId())
                .withDescription(categoryItemCursor.getDescription())
                .withName(categoryItemCursor.getName());
    }
}
