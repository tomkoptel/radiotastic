package com.udacity.study.jam.radiotastic.db.categoryitem;

import com.udacity.study.jam.radiotastic.db.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
