package com.udacity.study.jam.radiotastic.db.station;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.udacity.study.jam.radiotastic.db.base.AbstractSelection;

/**
 * Selection for the {@code station} table.
 */
public class StationSelection extends AbstractSelection<StationSelection> {
    @Override
    protected Uri baseUri() {
        return StationColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code StationCursor} object, which is positioned before the first entry, or null.
     */
    public StationCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new StationCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public StationCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public StationCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public StationSelection id(long... value) {
        addEquals("station." + StationColumns._ID, toObjectArray(value));
        return this;
    }

    public StationSelection stationId(long... value) {
        addEquals(StationColumns.STATION_ID, toObjectArray(value));
        return this;
    }

    public StationSelection stationIdNot(long... value) {
        addNotEquals(StationColumns.STATION_ID, toObjectArray(value));
        return this;
    }

    public StationSelection stationIdGt(long value) {
        addGreaterThan(StationColumns.STATION_ID, value);
        return this;
    }

    public StationSelection stationIdGtEq(long value) {
        addGreaterThanOrEquals(StationColumns.STATION_ID, value);
        return this;
    }

    public StationSelection stationIdLt(long value) {
        addLessThan(StationColumns.STATION_ID, value);
        return this;
    }

    public StationSelection stationIdLtEq(long value) {
        addLessThanOrEquals(StationColumns.STATION_ID, value);
        return this;
    }

    public StationSelection status(StationStatus... value) {
        addEquals(StationColumns.STATUS, value);
        return this;
    }

    public StationSelection statusNot(StationStatus... value) {
        addNotEquals(StationColumns.STATUS, value);
        return this;
    }


    public StationSelection name(String... value) {
        addEquals(StationColumns.NAME, value);
        return this;
    }

    public StationSelection nameNot(String... value) {
        addNotEquals(StationColumns.NAME, value);
        return this;
    }

    public StationSelection nameLike(String... value) {
        addLike(StationColumns.NAME, value);
        return this;
    }

    public StationSelection nameContains(String... value) {
        addContains(StationColumns.NAME, value);
        return this;
    }

    public StationSelection nameStartsWith(String... value) {
        addStartsWith(StationColumns.NAME, value);
        return this;
    }

    public StationSelection nameEndsWith(String... value) {
        addEndsWith(StationColumns.NAME, value);
        return this;
    }

    public StationSelection bitrate(String... value) {
        addEquals(StationColumns.BITRATE, value);
        return this;
    }

    public StationSelection bitrateNot(String... value) {
        addNotEquals(StationColumns.BITRATE, value);
        return this;
    }

    public StationSelection bitrateLike(String... value) {
        addLike(StationColumns.BITRATE, value);
        return this;
    }

    public StationSelection bitrateContains(String... value) {
        addContains(StationColumns.BITRATE, value);
        return this;
    }

    public StationSelection bitrateStartsWith(String... value) {
        addStartsWith(StationColumns.BITRATE, value);
        return this;
    }

    public StationSelection bitrateEndsWith(String... value) {
        addEndsWith(StationColumns.BITRATE, value);
        return this;
    }

    public StationSelection streamurl(String... value) {
        addEquals(StationColumns.STREAMURL, value);
        return this;
    }

    public StationSelection streamurlNot(String... value) {
        addNotEquals(StationColumns.STREAMURL, value);
        return this;
    }

    public StationSelection streamurlLike(String... value) {
        addLike(StationColumns.STREAMURL, value);
        return this;
    }

    public StationSelection streamurlContains(String... value) {
        addContains(StationColumns.STREAMURL, value);
        return this;
    }

    public StationSelection streamurlStartsWith(String... value) {
        addStartsWith(StationColumns.STREAMURL, value);
        return this;
    }

    public StationSelection streamurlEndsWith(String... value) {
        addEndsWith(StationColumns.STREAMURL, value);
        return this;
    }

    public StationSelection country(String... value) {
        addEquals(StationColumns.COUNTRY, value);
        return this;
    }

    public StationSelection countryNot(String... value) {
        addNotEquals(StationColumns.COUNTRY, value);
        return this;
    }

    public StationSelection countryLike(String... value) {
        addLike(StationColumns.COUNTRY, value);
        return this;
    }

    public StationSelection countryContains(String... value) {
        addContains(StationColumns.COUNTRY, value);
        return this;
    }

    public StationSelection countryStartsWith(String... value) {
        addStartsWith(StationColumns.COUNTRY, value);
        return this;
    }

    public StationSelection countryEndsWith(String... value) {
        addEndsWith(StationColumns.COUNTRY, value);
        return this;
    }

    public StationSelection website(String... value) {
        addEquals(StationColumns.WEBSITE, value);
        return this;
    }

    public StationSelection websiteNot(String... value) {
        addNotEquals(StationColumns.WEBSITE, value);
        return this;
    }

    public StationSelection websiteLike(String... value) {
        addLike(StationColumns.WEBSITE, value);
        return this;
    }

    public StationSelection websiteContains(String... value) {
        addContains(StationColumns.WEBSITE, value);
        return this;
    }

    public StationSelection websiteStartsWith(String... value) {
        addStartsWith(StationColumns.WEBSITE, value);
        return this;
    }

    public StationSelection websiteEndsWith(String... value) {
        addEndsWith(StationColumns.WEBSITE, value);
        return this;
    }

    public StationSelection description(String... value) {
        addEquals(StationColumns.DESCRIPTION, value);
        return this;
    }

    public StationSelection descriptionNot(String... value) {
        addNotEquals(StationColumns.DESCRIPTION, value);
        return this;
    }

    public StationSelection descriptionLike(String... value) {
        addLike(StationColumns.DESCRIPTION, value);
        return this;
    }

    public StationSelection descriptionContains(String... value) {
        addContains(StationColumns.DESCRIPTION, value);
        return this;
    }

    public StationSelection descriptionStartsWith(String... value) {
        addStartsWith(StationColumns.DESCRIPTION, value);
        return this;
    }

    public StationSelection descriptionEndsWith(String... value) {
        addEndsWith(StationColumns.DESCRIPTION, value);
        return this;
    }
}