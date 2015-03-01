package com.udacity.study.jam.radiotastic.data.cache.db.stationdata;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.study.jam.radiotastic.data.cache.db.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code station_data} table.
 */
public class StationDataCursor extends AbstractCursor implements StationDataModel {
    public StationDataCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(StationDataColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Represents id of object which resides on backend.
     */
    public long getStationId() {
        Long res = getLongOrNull(StationDataColumns.STATION_ID);
        if (res == null)
            throw new NullPointerException("The value of 'station_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Represents status of station. Either 1 UP or 0 DOWN
     * Cannot be {@code null}.
     */
    @NonNull
    public StationStatus getStatus() {
        Integer intValue = getIntegerOrNull(StationDataColumns.STATUS);
        if (intValue == null)
            throw new NullPointerException("The value of 'status' in the database was null, which is not allowed according to the model definition");
        return StationStatus.values()[intValue];
    }

    /**
     * Get the {@code name} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getName() {
        String res = getStringOrNull(StationDataColumns.NAME);
        if (res == null)
            throw new NullPointerException("The value of 'name' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code website} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getWebsite() {
        String res = getStringOrNull(StationDataColumns.WEBSITE);
        return res;
    }

    /**
     * Get the {@code streamurl} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getStreamurl() {
        String res = getStringOrNull(StationDataColumns.STREAMURL);
        if (res == null)
            throw new NullPointerException("The value of 'streamurl' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getDescription() {
        String res = getStringOrNull(StationDataColumns.DESCRIPTION);
        return res;
    }
}
