package com.udacity.study.jam.radiotastic.db.stationitem;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.study.jam.radiotastic.db.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code station_item} table.
 */
public class StationItemContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return StationItemColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable StationItemSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Represents id of object which resides on backend.
     */
    public StationItemContentValues putStationId(long value) {
        mContentValues.put(StationItemColumns.STATION_ID, value);
        return this;
    }


    /**
     * Represents status of station. Either 1 UP or 0 DOWN
     */
    public StationItemContentValues putStatus(@NonNull StationStatus value) {
        if (value == null) throw new IllegalArgumentException("status must not be null");
        mContentValues.put(StationItemColumns.STATUS, value.ordinal());
        return this;
    }


    public StationItemContentValues putName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("name must not be null");
        mContentValues.put(StationItemColumns.NAME, value);
        return this;
    }


    public StationItemContentValues putBitrate(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("bitrate must not be null");
        mContentValues.put(StationItemColumns.BITRATE, value);
        return this;
    }


    public StationItemContentValues putStreamurl(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("streamurl must not be null");
        mContentValues.put(StationItemColumns.STREAMURL, value);
        return this;
    }


    public StationItemContentValues putCountry(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("country must not be null");
        mContentValues.put(StationItemColumns.COUNTRY, value);
        return this;
    }

}
