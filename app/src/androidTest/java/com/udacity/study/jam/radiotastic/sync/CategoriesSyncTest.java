/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.sync;

import android.content.ContentResolver;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.study.jam.radiotastic.CategoryItem;
import com.udacity.study.jam.radiotastic.db.categoryitem.CategoryItemColumns;
import com.udacity.study.jam.radiotastic.db.categoryitem.CategoryItemContentValues;
import com.udacity.study.jam.radiotastic.util.CursorAssert;
import com.udacity.study.jam.radiotastic.util.MockRadioApi;
import com.udacity.study.jam.radiotastic.util.TestResource;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class CategoriesSyncTest extends AndroidTestCase {

    private ContentResolver contentResolver;

    private static final Uri CONTENT_URI = CategoryItemColumns.CONTENT_URI;
    private static final String[] ALL_COLUMNS = CategoryItemColumns.ALL_COLUMNS;
    private static final String SELECT_BY_CATEGORY_ID =
            CategoryItemColumns.CATEGORY_ID + "=?";

    @Mock
    MockRadioApi mockApi;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache",
                getContext().getCacheDir().getPath());

        MockitoAnnotations.initMocks(this);
        contentResolver = getContext().getContentResolver();
        contentResolver.delete(CONTENT_URI, null, null);
    }

    public void testInserts() {
        Collection<CategoryItem> items = createCategories("categories");
        when(mockApi.listPrimaryCategories()).thenReturn(items);

        SyncResult syncResult = new SyncResult();
        SyncTask categoriesSync = new SyncCategoriesCaseImpl(getContext(), syncResult, mockApi);

        categoriesSync.run();
        assertThat((int) syncResult.stats.numInserts, is(items.size()));

        assertItemsAgainstDb(items);
    }

    public void testUpdates() {
        populateDbWithCategories();

        Collection<CategoryItem> items = createCategories("categories_updated");
        when(mockApi.listPrimaryCategories()).thenReturn(items);

        SyncResult syncResult = new SyncResult();
        SyncTask categoriesSync = new SyncCategoriesCaseImpl(getContext(), syncResult, mockApi);

        categoriesSync.run();
        assertThat((int) syncResult.stats.numUpdates, is(2));

        assertItemsAgainstDb(items);
    }

    public void testDeletes() {
        populateDbWithCategories();

        Collection<CategoryItem> items = createCategories("categories_deleted");
        when(mockApi.listPrimaryCategories()).thenReturn(items);

        SyncResult syncResult = new SyncResult();
        SyncTask categoriesSync = new SyncCategoriesCaseImpl(getContext(), syncResult, mockApi);

        categoriesSync.run();
        assertThat((int) syncResult.stats.numDeletes, is(2));

        assertItemsAgainstDb(items);
    }

    private void populateDbWithCategories() {
        Collection<CategoryItem> initialItems = createCategories("categories");
        for (CategoryItem item : initialItems) {
            CategoryItemContentValues cv = new CategoryItemContentValues()
                    .putCategoryId((long) item.getId())
                    .putName(item.getName())
                    .putDescription(item.getDescription());
            contentResolver.insert(CONTENT_URI, cv.values());
        }
    }

    private void assertItemsAgainstDb(Collection<CategoryItem> items) {
        for (CategoryItem item : items) {
            Cursor cursor = contentResolver.query(CONTENT_URI, ALL_COLUMNS, SELECT_BY_CATEGORY_ID,
                    new String[] {String.valueOf(item.getId())}, null);
            CategoryItemContentValues cv = new CategoryItemContentValues()
                    .putCategoryId((long) item.getId())
                    .putName(item.getName())
                    .putDescription(item.getDescription());
            CursorAssert.validateCursor(cursor, cv.values());
        }
    }

    private Collection<CategoryItem> createCategories(String src) {
        String categories = TestResource.get(TestResource.DataFormat.JSON).rawData(src);
        Type collectionType = new TypeToken<List<CategoryItem>>() {
        }.getType();
        return new Gson().fromJson(categories, collectionType);
    }
}
