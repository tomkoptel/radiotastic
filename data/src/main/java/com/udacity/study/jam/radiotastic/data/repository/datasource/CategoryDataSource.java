/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.data.repository.datasource;

import com.udacity.study.jam.radiotastic.data.entity.CategoryEntity;

import java.util.Collection;

public interface CategoryDataSource {
    interface CategoryListCallback {
        void onCategoriesEntityListLoaded(Collection<CategoryEntity> categoryCollection);
        void onError(Exception exception);
    }

    void getCategoriesEntityList(CategoryListCallback categoryListCallback);
}
