package com.udacity.study.jam.radiotastic.db;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.udacity.study.jam.radiotastic.BuildConfig;
import com.udacity.study.jam.radiotastic.db.base.BaseContentProvider;
import com.udacity.study.jam.radiotastic.db.category.CategoryColumns;
import com.udacity.study.jam.radiotastic.db.station.StationColumns;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataColumns;

public class AppProvider extends BaseContentProvider {
    private static final String TAG = AppProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.udacity.study.jam.radiotastic.db.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_CATEGORY = 0;
    private static final int URI_TYPE_CATEGORY_ID = 1;

    private static final int URI_TYPE_STATION = 2;
    private static final int URI_TYPE_STATION_ID = 3;

    private static final int URI_TYPE_STATION_META_DATA = 4;
    private static final int URI_TYPE_STATION_META_DATA_ID = 5;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, CategoryColumns.TABLE_NAME, URI_TYPE_CATEGORY);
        URI_MATCHER.addURI(AUTHORITY, CategoryColumns.TABLE_NAME + "/#", URI_TYPE_CATEGORY_ID);
        URI_MATCHER.addURI(AUTHORITY, StationColumns.TABLE_NAME, URI_TYPE_STATION);
        URI_MATCHER.addURI(AUTHORITY, StationColumns.TABLE_NAME + "/#", URI_TYPE_STATION_ID);
        URI_MATCHER.addURI(AUTHORITY, StationMetaDataColumns.TABLE_NAME, URI_TYPE_STATION_META_DATA);
        URI_MATCHER.addURI(AUTHORITY, StationMetaDataColumns.TABLE_NAME + "/#", URI_TYPE_STATION_META_DATA_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return AppSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_CATEGORY:
                return TYPE_CURSOR_DIR + CategoryColumns.TABLE_NAME;
            case URI_TYPE_CATEGORY_ID:
                return TYPE_CURSOR_ITEM + CategoryColumns.TABLE_NAME;

            case URI_TYPE_STATION:
                return TYPE_CURSOR_DIR + StationColumns.TABLE_NAME;
            case URI_TYPE_STATION_ID:
                return TYPE_CURSOR_ITEM + StationColumns.TABLE_NAME;

            case URI_TYPE_STATION_META_DATA:
                return TYPE_CURSOR_DIR + StationMetaDataColumns.TABLE_NAME;
            case URI_TYPE_STATION_META_DATA_ID:
                return TYPE_CURSOR_ITEM + StationMetaDataColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_CATEGORY:
            case URI_TYPE_CATEGORY_ID:
                res.table = CategoryColumns.TABLE_NAME;
                res.idColumn = CategoryColumns._ID;
                res.tablesWithJoins = CategoryColumns.TABLE_NAME;
                res.orderBy = CategoryColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_STATION:
            case URI_TYPE_STATION_ID:
                res.table = StationColumns.TABLE_NAME;
                res.idColumn = StationColumns._ID;
                res.tablesWithJoins = StationColumns.TABLE_NAME;
                res.orderBy = StationColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_STATION_META_DATA:
            case URI_TYPE_STATION_META_DATA_ID:
                res.table = StationMetaDataColumns.TABLE_NAME;
                res.idColumn = StationMetaDataColumns._ID;
                res.tablesWithJoins = StationMetaDataColumns.TABLE_NAME;
                res.orderBy = StationMetaDataColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_CATEGORY_ID:
            case URI_TYPE_STATION_ID:
            case URI_TYPE_STATION_META_DATA_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
