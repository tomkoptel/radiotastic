package com.udacity.study.jam.radiotastic.data.cache.db.stationdata;

import com.udacity.study.jam.radiotastic.data.cache.db.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Overall available information regarding station. Provides more robust info regarding station.
 */
public interface StationDataModel extends BaseModel {

    /**
     * Represents id of object which resides on backend.
     */
    long getStationId();

    /**
     * Represents status of station. Either 1 UP or 0 DOWN
     * Cannot be {@code null}.
     */
    @NonNull
    StationStatus getStatus();

    /**
     * Get the {@code name} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getName();

    /**
     * Get the {@code website} value.
     * Can be {@code null}.
     */
    @Nullable
    String getWebsite();

    /**
     * Get the {@code streamurl} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getStreamurl();

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    String getDescription();
}
