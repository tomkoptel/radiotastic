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

import com.udacity.study.jam.radiotastic.data.cache.db.categoryitem.CategoryItemColumns;
import com.udacity.study.jam.radiotastic.data.cache.db.categoryitem.CategoryItemContentValues;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class CategoryDbTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext.getContentResolver().delete(CategoryItemColumns.CONTENT_URI, null, null);
        Cursor cursor = mContext.getContentResolver().query(CategoryItemColumns.CONTENT_URI,
                new String[]{"_id"}, null, null, null);
        assertThat(cursor.getCount(), is(0));
    }

    // Constraints assertions

    public void testExternalIdIsUnique() {
        ContentValues contentValues = createValidContentValues();
        Uri firstUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI, contentValues);
        assertThat(firstUri, is(notNullValue()));

        ContentValues notUniqueContentValues = createValidContentValues();
        notUniqueContentValues.put(CategoryItemColumns.NAME, "Name 1");
        Uri newUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI, notUniqueContentValues);
        assertThat(newUri, is(nullValue()));
    }

    public void testNameIsUnique() {
        ContentValues contentValues = createValidContentValues();
        Uri firstUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI, contentValues);
        assertThat(firstUri, is(notNullValue()));


        ContentValues notUniqueContentValues = createValidContentValues();
        notUniqueContentValues.put(CategoryItemColumns.CATEGORY_ID, 11);
        Uri newUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI, notUniqueContentValues);
        assertThat(newUri, is(nullValue()));
    }

    public void testNameIsNotNull() {
        String name = null;
        ContentValues contentValues = createValidContentValues();
        contentValues.put(CategoryItemColumns.NAME, name);

        try {
            mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI, contentValues);
            fail("Should not accept null value for name");
        } catch (SQLiteConstraintException exception) {
            assertThat(exception.getMessage(), containsString("NOT NULL constraint failed"));
        }
    }

    // CRUD assertions

    public void testInsert() {
        Uri newUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI,
                createValidContentValues());
        assertThat(newUri, is(notNullValue()));
        assertThat(ContentUris.parseId(newUri), is(not(0l)));
    }

    public void testUpdate() {
        ContentValues initialContentValues = createValidContentValues();
        Uri newUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI,
                createValidContentValues());

        initialContentValues.put(CategoryItemColumns.DESCRIPTION, "Description");

        int numberOfUpdatedRows = mContext.getContentResolver()
                .update(newUri, initialContentValues, null, null);
        assertThat(numberOfUpdatedRows, is(1));
    }

    public void testDelete() {
        Uri newUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI,
                createValidContentValues());

        int numberOfDeleted = mContext.getContentResolver().delete(newUri, null, null);
        assertThat(numberOfDeleted, is(1));
    }

    @NonNull
    private ContentValues createValidContentValues() {
        CategoryItemContentValues categoryContentValues = new CategoryItemContentValues();
        categoryContentValues.putCategoryId(10).putDescriptionNull().putName("Name");

        return categoryContentValues.values();
    }
}
