/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.test.AndroidTestCase;

import com.udacity.study.jam.radiotastic.data.cache.db.stationitem.StationItemColumns;
import com.udacity.study.jam.radiotastic.data.cache.db.stationitem.StationItemContentValues;
import com.udacity.study.jam.radiotastic.data.cache.db.stationitem.StationStatus;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class StationItemDbTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext.getContentResolver().delete(StationItemColumns.CONTENT_URI, null, null);
        Cursor cursor = mContext.getContentResolver().query(StationItemColumns.CONTENT_URI,
                new String[]{"_id"}, null, null, null);
        assertThat(cursor.getCount(), is(0));
    }

    // Constraints assertions

    public void testStationIdIsUnique() {
        ContentValues validContentValues = createValidContentValues();
        Uri firstUri = mContext.getContentResolver().insert(StationItemColumns.CONTENT_URI, validContentValues);
        assertThat(firstUri, is(notNullValue()));


        ContentValues duplicateContentValues = createValidContentValues();
        Uri nextUri = mContext.getContentResolver().insert(StationItemColumns.CONTENT_URI, duplicateContentValues);
        assertThat(nextUri, is(nullValue()));
    }

    public void testStatusIsNotNull() {
        assertFieldToBeNotNull(StationItemColumns.STATUS);
    }

    public void testNameIsNotNull() {
        assertFieldToBeNotNull(StationItemColumns.NAME);
    }

    public void testBitRateIsNotNull() {
        assertFieldToBeNotNull(StationItemColumns.BITRATE);
    }

    public void testCountryIsNotNull() {
        assertFieldToBeNotNull(StationItemColumns.COUNTRY);
    }

    private void assertFieldToBeNotNull(String field) {
        ContentValues notValidContentValues = createValidContentValues();
        String value = null;
        notValidContentValues.put(field, value);

        try {
            mContext.getContentResolver().insert(StationItemColumns.CONTENT_URI, notValidContentValues);
            fail(field + " should not be null");
        } catch (SQLiteConstraintException ex) {
            assertThat(ex.getMessage(), containsString("NOT NULL constraint failed"));
        }
    }

    // CRUD assertions

    public void testInsert() {
        Uri newUri = mContext.getContentResolver().insert(StationItemColumns.CONTENT_URI,
                createValidContentValues());
        assertThat(newUri, is(notNullValue()));
        assertThat(ContentUris.parseId(newUri), is(not(0l)));
    }

    public void testUpdate() {
        ContentValues initialValues = createValidContentValues();
        Uri newUri = mContext.getContentResolver().insert(StationItemColumns.CONTENT_URI,
                initialValues);

        initialValues.put(StationItemColumns.COUNTRY, "UA");

        int numberOfUpdatedRows = mContext.getContentResolver().update(newUri, initialValues, null, null);
        assertThat(numberOfUpdatedRows, is(1));
    }

    public void testDelete() {
        Uri newUri = mContext.getContentResolver().insert(StationItemColumns.CONTENT_URI,
                createValidContentValues());

        int numberOfDeleted = mContext.getContentResolver().delete(newUri, null, null);
        assertThat(numberOfDeleted, is(1));
    }

    @NonNull
    private ContentValues createValidContentValues() {
        StationItemContentValues stationItemContentValues = new StationItemContentValues();
        stationItemContentValues
                .putStationId(10)
                .putName("name")
                .putStatus(StationStatus.DOWN)
                .putStreamurl("stream")
                .putBitrate("bitrate")
                .putCountry("USA");

        return stationItemContentValues.values();
    }
}
