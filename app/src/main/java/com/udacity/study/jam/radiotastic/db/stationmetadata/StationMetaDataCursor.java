package com.udacity.study.jam.radiotastic.db.stationmetadata;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.study.jam.radiotastic.db.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code station_meta_data} table.
 */
public class StationMetaDataCursor extends AbstractCursor implements StationMetaDataModel {
    public StationMetaDataCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(StationMetaDataColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Represents id of object which resides on backend.
     */
    public long getStationId() {
        Long res = getLongOrNull(StationMetaDataColumns.STATION_ID);
        if (res == null)
            throw new NullPointerException("The value of 'station_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code meta} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMeta() {
        String res = getStringOrNull(StationMetaDataColumns.META);
        return res;
    }

    /**
     * Get the {@code created_at} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public Date getCreatedAt() {
        Date res = getDateOrNull(StationMetaDataColumns.CREATED_AT);
        if (res == null)
            throw new NullPointerException("The value of 'created_at' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
