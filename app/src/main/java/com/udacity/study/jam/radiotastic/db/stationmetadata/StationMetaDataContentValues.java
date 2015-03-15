package com.udacity.study.jam.radiotastic.db.stationmetadata;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.study.jam.radiotastic.db.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code station_meta_data} table.
 */
public class StationMetaDataContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return StationMetaDataColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable StationMetaDataSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Represents id of object which resides on backend.
     */
    public StationMetaDataContentValues putStationId(long value) {
        mContentValues.put(StationMetaDataColumns.STATION_ID, value);
        return this;
    }


    public StationMetaDataContentValues putMeta(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("meta must not be null");
        mContentValues.put(StationMetaDataColumns.META, value);
        return this;
    }


    public StationMetaDataContentValues putCreatedAt(@NonNull Date value) {
        if (value == null) throw new IllegalArgumentException("createdAt must not be null");
        mContentValues.put(StationMetaDataColumns.CREATED_AT, value.getTime());
        return this;
    }


    public StationMetaDataContentValues putCreatedAt(long value) {
        mContentValues.put(StationMetaDataColumns.CREATED_AT, value);
        return this;
    }
}
