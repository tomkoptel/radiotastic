/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.data.mapper.cursor;

import com.udacity.study.jam.radiotastic.Category;
import com.udacity.study.jam.radiotastic.data.cache.db.categoryitem.CategoryItemCursor;

public class CategoryCursorDataMapper {

    public Category transform(CategoryItemCursor categoryItemCursor) {
        Category category = new Category();
        category.setId(categoryItemCursor.getId());
        category.setName(categoryItemCursor.getName());
        category.setDescription(categoryItemCursor.getDescription());
        return category;
    }

}
