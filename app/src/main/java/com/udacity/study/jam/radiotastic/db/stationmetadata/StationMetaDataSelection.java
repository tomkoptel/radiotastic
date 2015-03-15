package com.udacity.study.jam.radiotastic.db.stationmetadata;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.udacity.study.jam.radiotastic.db.base.AbstractSelection;

/**
 * Selection for the {@code station_meta_data} table.
 */
public class StationMetaDataSelection extends AbstractSelection<StationMetaDataSelection> {
    @Override
    protected Uri baseUri() {
        return StationMetaDataColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code StationMetaDataCursor} object, which is positioned before the first entry, or null.
     */
    public StationMetaDataCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new StationMetaDataCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public StationMetaDataCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public StationMetaDataCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public StationMetaDataSelection id(long... value) {
        addEquals("station_meta_data." + StationMetaDataColumns._ID, toObjectArray(value));
        return this;
    }

    public StationMetaDataSelection stationId(long... value) {
        addEquals(StationMetaDataColumns.STATION_ID, toObjectArray(value));
        return this;
    }

    public StationMetaDataSelection stationIdNot(long... value) {
        addNotEquals(StationMetaDataColumns.STATION_ID, toObjectArray(value));
        return this;
    }

    public StationMetaDataSelection stationIdGt(long value) {
        addGreaterThan(StationMetaDataColumns.STATION_ID, value);
        return this;
    }

    public StationMetaDataSelection stationIdGtEq(long value) {
        addGreaterThanOrEquals(StationMetaDataColumns.STATION_ID, value);
        return this;
    }

    public StationMetaDataSelection stationIdLt(long value) {
        addLessThan(StationMetaDataColumns.STATION_ID, value);
        return this;
    }

    public StationMetaDataSelection stationIdLtEq(long value) {
        addLessThanOrEquals(StationMetaDataColumns.STATION_ID, value);
        return this;
    }

    public StationMetaDataSelection meta(String... value) {
        addEquals(StationMetaDataColumns.META, value);
        return this;
    }

    public StationMetaDataSelection metaNot(String... value) {
        addNotEquals(StationMetaDataColumns.META, value);
        return this;
    }

    public StationMetaDataSelection metaLike(String... value) {
        addLike(StationMetaDataColumns.META, value);
        return this;
    }

    public StationMetaDataSelection metaContains(String... value) {
        addContains(StationMetaDataColumns.META, value);
        return this;
    }

    public StationMetaDataSelection metaStartsWith(String... value) {
        addStartsWith(StationMetaDataColumns.META, value);
        return this;
    }

    public StationMetaDataSelection metaEndsWith(String... value) {
        addEndsWith(StationMetaDataColumns.META, value);
        return this;
    }

    public StationMetaDataSelection createdAt(Date... value) {
        addEquals(StationMetaDataColumns.CREATED_AT, value);
        return this;
    }

    public StationMetaDataSelection createdAtNot(Date... value) {
        addNotEquals(StationMetaDataColumns.CREATED_AT, value);
        return this;
    }

    public StationMetaDataSelection createdAt(long... value) {
        addEquals(StationMetaDataColumns.CREATED_AT, toObjectArray(value));
        return this;
    }

    public StationMetaDataSelection createdAtAfter(Date value) {
        addGreaterThan(StationMetaDataColumns.CREATED_AT, value);
        return this;
    }

    public StationMetaDataSelection createdAtAfterEq(Date value) {
        addGreaterThanOrEquals(StationMetaDataColumns.CREATED_AT, value);
        return this;
    }

    public StationMetaDataSelection createdAtBefore(Date value) {
        addLessThan(StationMetaDataColumns.CREATED_AT, value);
        return this;
    }

    public StationMetaDataSelection createdAtBeforeEq(Date value) {
        addLessThanOrEquals(StationMetaDataColumns.CREATED_AT, value);
        return this;
    }
}
