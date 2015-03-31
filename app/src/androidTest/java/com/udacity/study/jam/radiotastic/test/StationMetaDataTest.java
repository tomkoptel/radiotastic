/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.support.annotation.NonNull;
import android.test.AndroidTestCase;

import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataColumns;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataContentValues;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

public class StationMetaDataTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext.getContentResolver().delete(StationMetaDataColumns.CONTENT_URI, null, null);
        Cursor cursor = mContext.getContentResolver().query(StationMetaDataColumns.CONTENT_URI,
                new String[]{"_id"}, null, null, null);
        assertThat(cursor.getCount(), is(0));
    }

    public void testCreatedAtFieldShouldNotAcceptNull() {
        assertFieldToBeNotNull(StationMetaDataColumns.CREATED_AT);
    }

    private void assertFieldToBeNotNull(String field) {
        ContentValues notValidContentValues = createValidContentValues();
        String value = null;
        notValidContentValues.put(field, value);

        try {
            mContext.getContentResolver().insert(StationMetaDataColumns.CONTENT_URI, notValidContentValues);
            fail(field + " should not be null");
        } catch (SQLiteConstraintException ex) {
            assertThat(ex.getMessage(), containsString("NULL"));
        }
    }

    @NonNull
    private ContentValues createValidContentValues() {
        StationMetaDataContentValues stationMetaDataContentValues =
                new StationMetaDataContentValues();
        stationMetaDataContentValues
                .putStationId(10)
                .putMeta("{}")
                .putCreatedAt(new Date());
        return stationMetaDataContentValues.values();
    }

}
