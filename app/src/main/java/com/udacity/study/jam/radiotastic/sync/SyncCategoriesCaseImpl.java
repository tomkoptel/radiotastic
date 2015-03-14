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
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;

import com.udacity.study.jam.radiotastic.CategoryItem;
import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.api.RadioApi;
import com.udacity.study.jam.radiotastic.data.transform.CategoryCursorTransformer;
import com.udacity.study.jam.radiotastic.db.category.CategoryColumns;
import com.udacity.study.jam.radiotastic.db.category.CategoryCursor;
import com.udacity.study.jam.radiotastic.domain.SyncCategoriesCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit.RetrofitError;
import timber.log.Timber;

public class SyncCategoriesCaseImpl implements SyncCategoriesCase {

    private final Context context;
    private final SyncResult syncResult;
    private final RadioApi radioApi;
    private final CategoryCursorTransformer transformer =
            new CategoryCursorTransformer();

    @Inject
    public SyncCategoriesCaseImpl(Context context,
                                  SyncResult syncResult,
                                  RadioApi radioApi) {
        this.context = context;
        this.syncResult = syncResult;
        this.radioApi = radioApi;
    }

    @Override
    public void execute(Bundle args) {
        try {
            Collection<CategoryItem> categories = radioApi.listPrimaryCategories();
            // FileRadioApi implementation returns null due to IOException
            if (categories == null) {
                syncResult.stats.numIoExceptions++;
            } else {
                ContentResolver contentResolver = context.getContentResolver();
                Cursor cursor = contentResolver.query(CategoryColumns.CONTENT_URI, CategoryColumns.ALL_COLUMNS, null, null, null);
                try {
                    contentResolver.applyBatch(
                            context.getString(R.string.content_authority),
                            prepareBatch(cursor, categories)
                    );
                } catch (RemoteException e) {
                    Timber.e(e, "RemoteException while sync of categories");
                } catch (OperationApplicationException e) {
                    Timber.e(e, "OperationApplicationException while sync of categories");
                    throw new IllegalStateException(e);
                }
            }
        } catch (RetrofitError error) {
            syncResult.stats.numIoExceptions++;
        }
    }

    private ArrayList<ContentProviderOperation> prepareBatch(
            Cursor cursor, Collection<CategoryItem> serverCategories) {
        ArrayList<ContentProviderOperation> batch = new ArrayList<>();
        Map<Double, CategoryItem> map = new HashMap<Double, CategoryItem>();
        for (CategoryItem item : serverCategories) {
            map.put(item.getId(), item);
        }

        Uri itemUri;
        Uri tableUri = CategoryColumns.CONTENT_URI;
        double canonicalId;
        CategoryItem dbItem;
        CategoryCursor itemCursor;
        try {
            while (cursor.moveToNext()) {
                itemCursor = new CategoryCursor(cursor);
                dbItem = transformer.transform(itemCursor);

                itemUri = Uri.withAppendedPath(tableUri, String.valueOf(itemCursor.getId()));
                canonicalId = itemCursor.getCategoryId();

                CategoryItem webItem = map.get(canonicalId);
                if (webItem == null) {
                    syncResult.stats.numDeletes++;
                    batch.add(ContentProviderOperation.newDelete(itemUri).build());
                } else {
                    map.remove(canonicalId);

                    if (!dbItem.equals(webItem)) {
                        syncResult.stats.numUpdates++;
                        batch.add(
                                ContentProviderOperation.newUpdate(itemUri)
                                        .withValue(CategoryColumns.DESCRIPTION, webItem.getDescription())
                                        .withValue(CategoryColumns.NAME, webItem.getName())
                                        .build()
                        );
                    }
                }
            }
        } finally {
            cursor.close();
        }

        for (CategoryItem webItem : map.values()) {
            syncResult.stats.numInserts++;
            batch.add(ContentProviderOperation.newInsert(tableUri)
                    .withValue(CategoryColumns.DESCRIPTION, webItem.getDescription())
                    .withValue(CategoryColumns.NAME, webItem.getName())
                    .withValue(CategoryColumns.CATEGORY_ID, webItem.getId())
                    .build());
        }

        return batch;
    }

}
