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
import android.test.AndroidTestCase;

import com.udacity.study.jam.radiotastic.db.categoryitem.CategoryItemColumns;
import com.udacity.study.jam.radiotastic.db.categoryitem.CategoryItemContentValues;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
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
        CategoryItemContentValues categoryContentValues = new CategoryItemContentValues();
        categoryContentValues.putExternalId(10).putDescriptionNull().putName("Name");

        ContentValues contentValues = categoryContentValues.values();
        Uri firstUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI, contentValues);
        assertThat(firstUri, is(notNullValue()));

        ContentValues notUniqueContentValues = categoryContentValues.putName("Name 1").values();
        Uri newUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI, notUniqueContentValues);
        assertThat(newUri, is(nullValue()));
    }

    public void testNameIsUnique() {
        CategoryItemContentValues categoryContentValues = new CategoryItemContentValues();
        categoryContentValues.putExternalId(10).putDescriptionNull().putName("Name");

        ContentValues contentValues = categoryContentValues.values();
        Uri firstUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI, contentValues);
        assertThat(firstUri, is(notNullValue()));

        ContentValues notUniqueContentValues = categoryContentValues.putExternalId(11).values();
        Uri newUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI, notUniqueContentValues);
        assertThat(newUri, is(nullValue()));
    }

    public void testNameIsNotNull() {
        CategoryItemContentValues categoryContentValues = new CategoryItemContentValues();
        categoryContentValues.putExternalId(10).putDescriptionNull().putName("");

        String name = null;
        ContentValues contentValues = categoryContentValues.values();
        contentValues.put(CategoryItemColumns.NAME, name);

        try {
            mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI, contentValues);
            fail("Should not accept null value for name");
        } catch (SQLiteConstraintException exception) {
            assertThat(exception.getMessage(), is("category_item.name may not be NULL (code 19)"));
        }
    }

    // CRUD assertions

    public void testInsert() {
        CategoryItemContentValues categoryContentValues = new CategoryItemContentValues();
        categoryContentValues.putExternalId(10).putDescriptionNull().putName("Name");

        Uri newUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI,
                categoryContentValues.values());
        assertThat(newUri, is(notNullValue()));
        assertThat(ContentUris.parseId(newUri), is(not(0l)));
    }

    public void testUpdate() {
        CategoryItemContentValues categoryContentValues = new CategoryItemContentValues();
        categoryContentValues.putExternalId(10).putDescriptionNull().putName("Name");
        Uri newUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI,
                categoryContentValues.values());

        categoryContentValues.putDescription("Description");

        int numberOfUpdatedRows = mContext.getContentResolver().update(newUri, categoryContentValues.values(), null, null);
        assertThat(numberOfUpdatedRows, is(1));
    }


    public void testDelete() {
        CategoryItemContentValues categoryContentValues = new CategoryItemContentValues();
        categoryContentValues.putExternalId(10).putDescriptionNull().putName("Name");
        Uri newUri = mContext.getContentResolver().insert(CategoryItemColumns.CONTENT_URI,
                categoryContentValues.values());

        int numberOfDeleted = mContext.getContentResolver().delete(newUri, null, null);
        assertThat(numberOfDeleted, is(1));
    }
}
