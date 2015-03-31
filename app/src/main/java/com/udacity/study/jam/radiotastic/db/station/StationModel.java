package com.udacity.study.jam.radiotastic.db.station;

import com.udacity.study.jam.radiotastic.db.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * General station info. This is basic set of data enough for client to playback.
 */
public interface StationModel extends BaseModel {

    /**
     * Represents id of object which resides on backend.
     */
    long getStationId();

    /**
     * Represents id of asscociated category with specific station.
     */
    long getCategoryId();

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
     * Get the {@code bitrate} value.
     */
    int getBitrate();

    /**
     * Get the {@code streamurl} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getStreamurl();

    /**
     * Get the {@code country} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getCountry();

    /**
     * Get the {@code website} value.
     * Can be {@code null}.
     */
    @Nullable
    String getWebsite();

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    String getDescription();
}
