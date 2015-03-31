/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.test.sync;

import android.content.ContentResolver;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.study.jam.radiotastic.StationItem;
import com.udacity.study.jam.radiotastic.api.RadioApi;
import com.udacity.study.jam.radiotastic.db.station.StationColumns;
import com.udacity.study.jam.radiotastic.db.station.StationContentValues;
import com.udacity.study.jam.radiotastic.db.station.StationStatus;
import com.udacity.study.jam.radiotastic.sync.SyncStationsCaseImpl;
import com.udacity.study.jam.radiotastic.sync.SyncTask;
import com.udacity.study.jam.radiotastic.test.util.CursorAssert;
import com.udacity.study.jam.radiotastic.test.util.TestResource;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StationsSyncTest extends AndroidTestCase {

    private ContentResolver contentResolver;

    private static final Uri CONTENT_URI = StationColumns.CONTENT_URI;
    private static final String CATEGORY_ID = "100";
    private static final String[] ALL_COLUMNS = {
            StationColumns._ID,
            StationColumns.CATEGORY_ID,
            StationColumns.STATION_ID,
            StationColumns.NAME,
            StationColumns.WEBSITE,
            StationColumns.STREAMURL,
            StationColumns.COUNTRY,
            StationColumns.BITRATE,
            StationColumns.STATUS,
    };
    private static final String SELECT_BY_CATEGORY_AND_STATION_ID =
            StationColumns.CATEGORY_ID + "=? AND " + StationColumns.STATION_ID + "=?";
    private static final String INSERT_BUCKET_SRC = "stations";
    private static final String UPDATE_BUCKET_SRC = "stations_updated";
    private static final String DELETE_BUCKET_SRC = "stations_deleted";

    private RadioApi mockApi;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache",
                getContext().getCacheDir().getPath());

        mockApi = mock(RadioApi.class);
        contentResolver = getContext().getContentResolver();
        contentResolver.delete(CONTENT_URI, null, null);
    }

    public void testInserts() {
        Collection<StationItem> items = createStations(INSERT_BUCKET_SRC);
        when(mockApi.listStations(anyString())).thenReturn(items);

        SyncResult syncResult = new SyncResult();
        SyncTask categoriesSync = new SyncStationsCaseImpl(getContext(), syncResult, mockApi);

        Bundle args = prepareArgs();
        categoriesSync.execute(args);
        assertThat((int) syncResult.stats.numInserts, is(items.size()));

        assertItemsAgainstDb(items);
    }

    public void testUpdates() {
        populateDbWithCategories();

        Collection<StationItem> items = createStations(UPDATE_BUCKET_SRC);
        when(mockApi.listStations(anyString())).thenReturn(items);

        SyncResult syncResult = new SyncResult();
        SyncTask categoriesSync = new SyncStationsCaseImpl(getContext(), syncResult, mockApi);

        Bundle args = prepareArgs();
        categoriesSync.execute(args);
        assertThat((int) syncResult.stats.numUpdates, is(2));

        assertItemsAgainstDb(items);
    }

    public void testDeletes() {
        populateDbWithCategories();

        Collection<StationItem> items = createStations(DELETE_BUCKET_SRC);
        when(mockApi.listStations(anyString())).thenReturn(items);

        SyncResult syncResult = new SyncResult();
        SyncTask categoriesSync = new SyncStationsCaseImpl(getContext(), syncResult, mockApi);

        Bundle args = prepareArgs();
        categoriesSync.execute(args);
        assertThat((int) syncResult.stats.numDeletes, is(2));

        assertItemsAgainstDb(items);
    }


    private Bundle prepareArgs() {
        Bundle args = new Bundle();
        args.putString(SyncStationsCaseImpl.CATEGORY_ID_ARG, CATEGORY_ID);
        return args;
    }

    private void populateDbWithCategories() {
        Collection<StationItem> initialItems = createStations(INSERT_BUCKET_SRC);
        for (StationItem item : initialItems) {
            StationContentValues cv = transform(item);
            contentResolver.insert(CONTENT_URI, cv.values());
        }
    }

    private void assertItemsAgainstDb(Collection<StationItem> items) {
        for (StationItem item : items) {
            Cursor cursor = contentResolver.query(CONTENT_URI, ALL_COLUMNS, SELECT_BY_CATEGORY_AND_STATION_ID,
                    new String[] {CATEGORY_ID, String.valueOf(item.getId())}, null);
            StationContentValues cv = transform(item);
            CursorAssert.validateCursor(cursor, cv.values());
        }
    }

    private StationContentValues transform(StationItem item) {
        return new StationContentValues()
                .putCategoryId(Long.valueOf(CATEGORY_ID))
                .putStationId((long) item.getId())
                .putName(item.getName())
                .putWebsite(item.getWebsite())
                .putStreamurl(item.getStreamurl())
                .putCountry(item.getCountry())
                .putBitrate(Integer.valueOf(item.getBitrate()))
                .putStatus(StationStatus.values()[item.getStatus()]);
    }

    private Collection<StationItem> createStations(String src) {
        String categories = TestResource.get(TestResource.DataFormat.JSON).rawData(src);
        Type collectionType = new TypeToken<List<StationItem>>() {
        }.getType();
        return new Gson().fromJson(categories, collectionType);
    }
}
