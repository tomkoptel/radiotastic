package com.udacity.study.jam.radiotastic.db.stationitem;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.study.jam.radiotastic.db.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code station_item} table.
 */
public class StationItemCursor extends AbstractCursor implements StationItemModel {
    public StationItemCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(StationItemColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Represents id of object which resides on backend.
     */
    public long getStationId() {
        Long res = getLongOrNull(StationItemColumns.STATION_ID);
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
        Integer intValue = getIntegerOrNull(StationItemColumns.STATUS);
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
        String res = getStringOrNull(StationItemColumns.NAME);
        if (res == null)
            throw new NullPointerException("The value of 'name' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code bitrate} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getBitrate() {
        String res = getStringOrNull(StationItemColumns.BITRATE);
        if (res == null)
            throw new NullPointerException("The value of 'bitrate' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code streamurl} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getStreamurl() {
        String res = getStringOrNull(StationItemColumns.STREAMURL);
        if (res == null)
            throw new NullPointerException("The value of 'streamurl' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code country} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getCountry() {
        String res = getStringOrNull(StationItemColumns.COUNTRY);
        if (res == null)
            throw new NullPointerException("The value of 'country' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
