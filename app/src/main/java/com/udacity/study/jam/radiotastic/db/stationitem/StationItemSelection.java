package com.udacity.study.jam.radiotastic.db.stationitem;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.udacity.study.jam.radiotastic.db.base.AbstractSelection;

/**
 * Selection for the {@code station_item} table.
 */
public class StationItemSelection extends AbstractSelection<StationItemSelection> {
    @Override
    protected Uri baseUri() {
        return StationItemColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code StationItemCursor} object, which is positioned before the first entry, or null.
     */
    public StationItemCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new StationItemCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public StationItemCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public StationItemCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public StationItemSelection id(long... value) {
        addEquals("station_item." + StationItemColumns._ID, toObjectArray(value));
        return this;
    }

    public StationItemSelection stationId(long... value) {
        addEquals(StationItemColumns.STATION_ID, toObjectArray(value));
        return this;
    }

    public StationItemSelection stationIdNot(long... value) {
        addNotEquals(StationItemColumns.STATION_ID, toObjectArray(value));
        return this;
    }

    public StationItemSelection stationIdGt(long value) {
        addGreaterThan(StationItemColumns.STATION_ID, value);
        return this;
    }

    public StationItemSelection stationIdGtEq(long value) {
        addGreaterThanOrEquals(StationItemColumns.STATION_ID, value);
        return this;
    }

    public StationItemSelection stationIdLt(long value) {
        addLessThan(StationItemColumns.STATION_ID, value);
        return this;
    }

    public StationItemSelection stationIdLtEq(long value) {
        addLessThanOrEquals(StationItemColumns.STATION_ID, value);
        return this;
    }

    public StationItemSelection status(StationStatus... value) {
        addEquals(StationItemColumns.STATUS, value);
        return this;
    }

    public StationItemSelection statusNot(StationStatus... value) {
        addNotEquals(StationItemColumns.STATUS, value);
        return this;
    }


    public StationItemSelection name(String... value) {
        addEquals(StationItemColumns.NAME, value);
        return this;
    }

    public StationItemSelection nameNot(String... value) {
        addNotEquals(StationItemColumns.NAME, value);
        return this;
    }

    public StationItemSelection nameLike(String... value) {
        addLike(StationItemColumns.NAME, value);
        return this;
    }

    public StationItemSelection nameContains(String... value) {
        addContains(StationItemColumns.NAME, value);
        return this;
    }

    public StationItemSelection nameStartsWith(String... value) {
        addStartsWith(StationItemColumns.NAME, value);
        return this;
    }

    public StationItemSelection nameEndsWith(String... value) {
        addEndsWith(StationItemColumns.NAME, value);
        return this;
    }

    public StationItemSelection bitrate(String... value) {
        addEquals(StationItemColumns.BITRATE, value);
        return this;
    }

    public StationItemSelection bitrateNot(String... value) {
        addNotEquals(StationItemColumns.BITRATE, value);
        return this;
    }

    public StationItemSelection bitrateLike(String... value) {
        addLike(StationItemColumns.BITRATE, value);
        return this;
    }

    public StationItemSelection bitrateContains(String... value) {
        addContains(StationItemColumns.BITRATE, value);
        return this;
    }

    public StationItemSelection bitrateStartsWith(String... value) {
        addStartsWith(StationItemColumns.BITRATE, value);
        return this;
    }

    public StationItemSelection bitrateEndsWith(String... value) {
        addEndsWith(StationItemColumns.BITRATE, value);
        return this;
    }

    public StationItemSelection streamurl(String... value) {
        addEquals(StationItemColumns.STREAMURL, value);
        return this;
    }

    public StationItemSelection streamurlNot(String... value) {
        addNotEquals(StationItemColumns.STREAMURL, value);
        return this;
    }

    public StationItemSelection streamurlLike(String... value) {
        addLike(StationItemColumns.STREAMURL, value);
        return this;
    }

    public StationItemSelection streamurlContains(String... value) {
        addContains(StationItemColumns.STREAMURL, value);
        return this;
    }

    public StationItemSelection streamurlStartsWith(String... value) {
        addStartsWith(StationItemColumns.STREAMURL, value);
        return this;
    }

    public StationItemSelection streamurlEndsWith(String... value) {
        addEndsWith(StationItemColumns.STREAMURL, value);
        return this;
    }

    public StationItemSelection country(String... value) {
        addEquals(StationItemColumns.COUNTRY, value);
        return this;
    }

    public StationItemSelection countryNot(String... value) {
        addNotEquals(StationItemColumns.COUNTRY, value);
        return this;
    }

    public StationItemSelection countryLike(String... value) {
        addLike(StationItemColumns.COUNTRY, value);
        return this;
    }

    public StationItemSelection countryContains(String... value) {
        addContains(StationItemColumns.COUNTRY, value);
        return this;
    }

    public StationItemSelection countryStartsWith(String... value) {
        addStartsWith(StationItemColumns.COUNTRY, value);
        return this;
    }

    public StationItemSelection countryEndsWith(String... value) {
        addEndsWith(StationItemColumns.COUNTRY, value);
        return this;
    }
}
