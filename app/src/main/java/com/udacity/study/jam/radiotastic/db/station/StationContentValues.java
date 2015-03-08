/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.db.station;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.study.jam.radiotastic.db.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code station} table.
 */
public class StationContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return StationColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable StationSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Represents id of object which resides on backend.
     */
    public StationContentValues putStationId(long value) {
        mContentValues.put(StationColumns.STATION_ID, value);
        return this;
    }


    /**
     * Represents status of station. Either 1 UP or 0 DOWN
     */
    public StationContentValues putStatus(@NonNull StationStatus value) {
        if (value == null) throw new IllegalArgumentException("status must not be null");
        mContentValues.put(StationColumns.STATUS, value.ordinal());
        return this;
    }


    public StationContentValues putName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("name must not be null");
        mContentValues.put(StationColumns.NAME, value);
        return this;
    }


    public StationContentValues putBitrate(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("bitrate must not be null");
        mContentValues.put(StationColumns.BITRATE, value);
        return this;
    }


    public StationContentValues putStreamurl(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("streamurl must not be null");
        mContentValues.put(StationColumns.STREAMURL, value);
        return this;
    }


    public StationContentValues putCountry(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("country must not be null");
        mContentValues.put(StationColumns.COUNTRY, value);
        return this;
    }


    public StationContentValues putWebsite(@Nullable String value) {
        mContentValues.put(StationColumns.WEBSITE, value);
        return this;
    }

    public StationContentValues putWebsiteNull() {
        mContentValues.putNull(StationColumns.WEBSITE);
        return this;
    }

    public StationContentValues putDescription(@Nullable String value) {
        mContentValues.put(StationColumns.DESCRIPTION, value);
        return this;
    }

    public StationContentValues putDescriptionNull() {
        mContentValues.putNull(StationColumns.DESCRIPTION);
        return this;
    }
}
