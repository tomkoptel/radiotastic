package com.udacity.study.jam.radiotastic.db.stationdata;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.udacity.study.jam.radiotastic.db.base.AbstractSelection;

/**
 * Selection for the {@code station_data} table.
 */
public class StationDataSelection extends AbstractSelection<StationDataSelection> {
    @Override
    protected Uri baseUri() {
        return StationDataColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code StationDataCursor} object, which is positioned before the first entry, or null.
     */
    public StationDataCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new StationDataCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public StationDataCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public StationDataCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public StationDataSelection id(long... value) {
        addEquals("station_data." + StationDataColumns._ID, toObjectArray(value));
        return this;
    }

    public StationDataSelection stationId(long... value) {
        addEquals(StationDataColumns.STATION_ID, toObjectArray(value));
        return this;
    }

    public StationDataSelection stationIdNot(long... value) {
        addNotEquals(StationDataColumns.STATION_ID, toObjectArray(value));
        return this;
    }

    public StationDataSelection stationIdGt(long value) {
        addGreaterThan(StationDataColumns.STATION_ID, value);
        return this;
    }

    public StationDataSelection stationIdGtEq(long value) {
        addGreaterThanOrEquals(StationDataColumns.STATION_ID, value);
        return this;
    }

    public StationDataSelection stationIdLt(long value) {
        addLessThan(StationDataColumns.STATION_ID, value);
        return this;
    }

    public StationDataSelection stationIdLtEq(long value) {
        addLessThanOrEquals(StationDataColumns.STATION_ID, value);
        return this;
    }

    public StationDataSelection status(StationStatus... value) {
        addEquals(StationDataColumns.STATUS, value);
        return this;
    }

    public StationDataSelection statusNot(StationStatus... value) {
        addNotEquals(StationDataColumns.STATUS, value);
        return this;
    }


    public StationDataSelection name(String... value) {
        addEquals(StationDataColumns.NAME, value);
        return this;
    }

    public StationDataSelection nameNot(String... value) {
        addNotEquals(StationDataColumns.NAME, value);
        return this;
    }

    public StationDataSelection nameLike(String... value) {
        addLike(StationDataColumns.NAME, value);
        return this;
    }

    public StationDataSelection nameContains(String... value) {
        addContains(StationDataColumns.NAME, value);
        return this;
    }

    public StationDataSelection nameStartsWith(String... value) {
        addStartsWith(StationDataColumns.NAME, value);
        return this;
    }

    public StationDataSelection nameEndsWith(String... value) {
        addEndsWith(StationDataColumns.NAME, value);
        return this;
    }

    public StationDataSelection website(String... value) {
        addEquals(StationDataColumns.WEBSITE, value);
        return this;
    }

    public StationDataSelection websiteNot(String... value) {
        addNotEquals(StationDataColumns.WEBSITE, value);
        return this;
    }

    public StationDataSelection websiteLike(String... value) {
        addLike(StationDataColumns.WEBSITE, value);
        return this;
    }

    public StationDataSelection websiteContains(String... value) {
        addContains(StationDataColumns.WEBSITE, value);
        return this;
    }

    public StationDataSelection websiteStartsWith(String... value) {
        addStartsWith(StationDataColumns.WEBSITE, value);
        return this;
    }

    public StationDataSelection websiteEndsWith(String... value) {
        addEndsWith(StationDataColumns.WEBSITE, value);
        return this;
    }

    public StationDataSelection streamurl(String... value) {
        addEquals(StationDataColumns.STREAMURL, value);
        return this;
    }

    public StationDataSelection streamurlNot(String... value) {
        addNotEquals(StationDataColumns.STREAMURL, value);
        return this;
    }

    public StationDataSelection streamurlLike(String... value) {
        addLike(StationDataColumns.STREAMURL, value);
        return this;
    }

    public StationDataSelection streamurlContains(String... value) {
        addContains(StationDataColumns.STREAMURL, value);
        return this;
    }

    public StationDataSelection streamurlStartsWith(String... value) {
        addStartsWith(StationDataColumns.STREAMURL, value);
        return this;
    }

    public StationDataSelection streamurlEndsWith(String... value) {
        addEndsWith(StationDataColumns.STREAMURL, value);
        return this;
    }

    public StationDataSelection description(String... value) {
        addEquals(StationDataColumns.DESCRIPTION, value);
        return this;
    }

    public StationDataSelection descriptionNot(String... value) {
        addNotEquals(StationDataColumns.DESCRIPTION, value);
        return this;
    }

    public StationDataSelection descriptionLike(String... value) {
        addLike(StationDataColumns.DESCRIPTION, value);
        return this;
    }

    public StationDataSelection descriptionContains(String... value) {
        addContains(StationDataColumns.DESCRIPTION, value);
        return this;
    }

    public StationDataSelection descriptionStartsWith(String... value) {
        addStartsWith(StationDataColumns.DESCRIPTION, value);
        return this;
    }

    public StationDataSelection descriptionEndsWith(String... value) {
        addEndsWith(StationDataColumns.DESCRIPTION, value);
        return this;
    }
}
