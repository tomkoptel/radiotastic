/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.sync;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.StationItem;
import com.udacity.study.jam.radiotastic.api.RadioApi;
import com.udacity.study.jam.radiotastic.data.transform.StationCursorTransformer;
import com.udacity.study.jam.radiotastic.db.station.StationColumns;
import com.udacity.study.jam.radiotastic.db.station.StationCursor;
import com.udacity.study.jam.radiotastic.domain.SyncStationsCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit.RetrofitError;
import timber.log.Timber;

public class SyncStationsCaseImpl implements SyncStationsCase {
    public static final String CATEGORY_ID_ARG = "CATEGORY_ID";

    private static final Uri CONTENT_URI = StationColumns.CONTENT_URI;
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
    private static final String SELECT_BY_CATEGORY_ID = StationColumns.CATEGORY_ID + "=?";

    private final Context context;
    private final SyncResult syncResult;
    private final RadioApi radioApi;
    private final StationCursorTransformer transformer =
            new StationCursorTransformer();

    @Inject
    public SyncStationsCaseImpl(Context context,
                                SyncResult syncResult,
                                RadioApi radioApi) {
        this.context = context;
        this.syncResult = syncResult;
        this.radioApi = radioApi;
    }

    @Override
    public void execute(Bundle args) {
        String categoryId = args.getString(CATEGORY_ID_ARG);
        try {
            Collection<StationItem> stations = radioApi.listStations(categoryId);
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(CONTENT_URI,
                    ALL_COLUMNS, SELECT_BY_CATEGORY_ID,
                    new String[]{categoryId}, null);
            try {
                contentResolver.applyBatch(
                        context.getString(R.string.content_authority),
                        prepareBatch(cursor, categoryId, stations)
                );
            } catch (RemoteException e) {
                Timber.e(e, "RemoteException while sync of categories");
            } catch (OperationApplicationException e) {
                Timber.e(e, "OperationApplicationException while sync of categories");
                throw new IllegalStateException(e);
            }
        } catch (RetrofitError error) {
            Timber.e(error, "Failed to sync stations");
            syncResult.stats.numIoExceptions++;
        }
    }

    private ArrayList<ContentProviderOperation> prepareBatch(Cursor cursor, String categoryId, Collection<StationItem> serverStations) {
        ArrayList<ContentProviderOperation> batch = new ArrayList<>();
        Map<Double, StationItem> map = new HashMap<Double, StationItem>();
        for (StationItem item : serverStations) {
            map.put(item.getId(), item);
        }

        Uri itemUri;
        double canonicalId;
        StationItem dbItem;
        StationCursor itemCursor;
        try {
            while (cursor.moveToNext()) {
                itemCursor = new StationCursor(cursor);
                dbItem = transformer.transform(itemCursor);

                itemUri = Uri.withAppendedPath(CONTENT_URI, String.valueOf(itemCursor.getId()));
                canonicalId = itemCursor.getStationId();

                StationItem webItem = map.get(canonicalId);
                if (webItem == null) {
                    syncResult.stats.numDeletes++;
                    batch.add(ContentProviderOperation.newDelete(itemUri).build());
                } else {
                    map.remove(canonicalId);

                    if (!dbItem.equals(webItem)) {
                        if (isItemValid(webItem)) {
                            syncResult.stats.numUpdates++;
                            batch.add(
                                    ContentProviderOperation.newUpdate(itemUri)
                                            .withValues(createContentValues(webItem))
                                            .build()
                            );
                        }
                    }
                }
            }
        } finally {
            cursor.close();
        }

        for (StationItem webItem : map.values()) {
            if (isItemValid(webItem)) {
                syncResult.stats.numInserts++;
                ContentValues contentValues = createContentValues(webItem);
                contentValues.put(StationColumns.STATION_ID, webItem.getId());
                contentValues.put(StationColumns.CATEGORY_ID, categoryId);
                batch.add(ContentProviderOperation.newInsert(CONTENT_URI)
                        .withValues(contentValues)
                        .build());
            }
        }

        return batch;
    }

    private boolean isItemValid(StationItem webItem) {
        return !TextUtils.isEmpty(webItem.getName());
    }

    public ContentValues createContentValues(StationItem webItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StationColumns.NAME, webItem.getName());
        contentValues.put(StationColumns.WEBSITE, webItem.getWebsite());
        contentValues.put(StationColumns.STREAMURL, webItem.getStreamurl());
        contentValues.put(StationColumns.COUNTRY, webItem.getCountry());
        contentValues.put(StationColumns.BITRATE, webItem.getBitrate());
        contentValues.put(StationColumns.STATUS, webItem.getStatus());
        return contentValues;
    }

}
