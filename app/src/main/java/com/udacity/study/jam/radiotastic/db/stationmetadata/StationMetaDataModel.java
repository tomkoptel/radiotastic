package com.udacity.study.jam.radiotastic.db.stationmetadata;

import com.udacity.study.jam.radiotastic.db.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * General station info. This is basic set of data enough for client to playback.
 */
public interface StationMetaDataModel extends BaseModel {

    /**
     * Represents id of object which resides on backend.
     */
    long getStationId();

    /**
     * Get the {@code meta} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getMeta();

    /**
     * Get the {@code created_at} value.
     * Cannot be {@code null}.
     */
    @NonNull
    Date getCreatedAt();
}
