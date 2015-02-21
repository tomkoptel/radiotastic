/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.udacity.study.jam.radiotastic.db.AppSQLiteOpenHelper;
import com.udacity.study.jam.radiotastic.db.categoryitem.CategoryItemColumns;
import com.udacity.study.jam.radiotastic.db.categoryitem.CategoryItemContentValues;

import java.util.Map;
import java.util.Set;

public class DatabaseTest extends AndroidTestCase {

    public void testCreateDb() {
        mContext.deleteDatabase(AppSQLiteOpenHelper.DATABASE_FILE_NAME);
        SQLiteDatabase db = AppSQLiteOpenHelper.getInstance(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertReadDb() {
        SQLiteDatabase db = AppSQLiteOpenHelper.getInstance(mContext).getWritableDatabase();
        CategoryItemContentValues categoryItemContentValues = new CategoryItemContentValues();
        ContentValues values = categoryItemContentValues
                .putExternalId(10)
                .putName("name")
                .putDescription("desc")
                .values();
        long rowId = db.insert(CategoryItemColumns.TABLE_NAME, null, values);
        assertTrue(rowId != -1);

        Cursor cursor = db.query(CategoryItemColumns.TABLE_NAME,
                CategoryItemColumns.ALL_COLUMNS, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            validateCursor(cursor, values);
        } else {
            fail("Category cursor is empty");
        }
    }

    static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {
        assertTrue(valueCursor.moveToFirst());

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, valueCursor.getString(idx));
        }
        valueCursor.close();
    }
}
