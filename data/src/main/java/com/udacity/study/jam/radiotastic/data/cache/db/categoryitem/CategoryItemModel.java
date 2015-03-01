/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.data.cache.db.categoryitem;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.study.jam.radiotastic.data.cache.db.base.BaseModel;

/**
 * A category being which represents group of stations.
 */
public interface CategoryItemModel extends BaseModel {

    /**
     * Represents id of object which resides on backend.
     */
    long getCategoryId();

    /**
     * Name of category
     * Cannot be {@code null}.
     */
    @NonNull
    String getName();

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    String getDescription();
}
