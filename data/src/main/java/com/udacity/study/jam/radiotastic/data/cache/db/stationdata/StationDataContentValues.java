package com.udacity.study.jam.radiotastic.data.cache.db.stationdata;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.study.jam.radiotastic.data.cache.db.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code station_data} table.
 */
public class StationDataContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return StationDataColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable StationDataSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Represents id of object which resides on backend.
     */
    public StationDataContentValues putStationId(long value) {
        mContentValues.put(StationDataColumns.STATION_ID, value);
        return this;
    }


    /**
     * Represents status of station. Either 1 UP or 0 DOWN
     */
    public StationDataContentValues putStatus(@NonNull StationStatus value) {
        if (value == null) throw new IllegalArgumentException("status must not be null");
        mContentValues.put(StationDataColumns.STATUS, value.ordinal());
        return this;
    }


    public StationDataContentValues putName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("name must not be null");
        mContentValues.put(StationDataColumns.NAME, value);
        return this;
    }


    public StationDataContentValues putWebsite(@Nullable String value) {
        mContentValues.put(StationDataColumns.WEBSITE, value);
        return this;
    }

    public StationDataContentValues putWebsiteNull() {
        mContentValues.putNull(StationDataColumns.WEBSITE);
        return this;
    }

    public StationDataContentValues putStreamurl(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("streamurl must not be null");
        mContentValues.put(StationDataColumns.STREAMURL, value);
        return this;
    }


    public StationDataContentValues putDescription(@Nullable String value) {
        mContentValues.put(StationDataColumns.DESCRIPTION, value);
        return this;
    }

    public StationDataContentValues putDescriptionNull() {
        mContentValues.putNull(StationDataColumns.DESCRIPTION);
        return this;
    }
}
