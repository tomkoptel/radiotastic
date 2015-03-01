package com.udacity.study.jam.radiotastic.data.cache.db;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.udacity.study.jam.radiotastic.data.BuildConfig;
import com.udacity.study.jam.radiotastic.data.cache.db.categoryitem.CategoryItemColumns;
import com.udacity.study.jam.radiotastic.data.cache.db.stationdata.StationDataColumns;
import com.udacity.study.jam.radiotastic.data.cache.db.stationitem.StationItemColumns;

public class AppSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = AppSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "radiotastic.db";
    private static final int DATABASE_VERSION = 1;
    private static AppSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final AppSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_CATEGORY_ITEM = "CREATE TABLE IF NOT EXISTS "
            + CategoryItemColumns.TABLE_NAME + " ( "
            + CategoryItemColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CategoryItemColumns.CATEGORY_ID + " INTEGER NOT NULL, "
            + CategoryItemColumns.NAME + " TEXT NOT NULL, "
            + CategoryItemColumns.DESCRIPTION + " TEXT "
            + ", CONSTRAINT unique_external_id UNIQUE (category_id) ON CONFLICT IGNORE"
            + ", CONSTRAINT unique_name UNIQUE (name) ON CONFLICT IGNORE"
            + " );";

    public static final String SQL_CREATE_INDEX_CATEGORY_ITEM_CATEGORY_ID = "CREATE INDEX IDX_CATEGORY_ITEM_CATEGORY_ID "
            + " ON " + CategoryItemColumns.TABLE_NAME + " ( " + CategoryItemColumns.CATEGORY_ID + " );";

    public static final String SQL_CREATE_TABLE_STATION_DATA = "CREATE TABLE IF NOT EXISTS "
            + StationDataColumns.TABLE_NAME + " ( "
            + StationDataColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StationDataColumns.STATION_ID + " INTEGER NOT NULL, "
            + StationDataColumns.STATUS + " INTEGER NOT NULL, "
            + StationDataColumns.NAME + " TEXT NOT NULL, "
            + StationDataColumns.WEBSITE + " TEXT, "
            + StationDataColumns.STREAMURL + " TEXT NOT NULL, "
            + StationDataColumns.DESCRIPTION + " TEXT "
            + ", CONSTRAINT unique_external_id UNIQUE (station_id) ON CONFLICT IGNORE"
            + " );";

    public static final String SQL_CREATE_INDEX_STATION_DATA_STATION_ID = "CREATE INDEX IDX_STATION_DATA_STATION_ID "
            + " ON " + StationDataColumns.TABLE_NAME + " ( " + StationDataColumns.STATION_ID + " );";

    public static final String SQL_CREATE_TABLE_STATION_ITEM = "CREATE TABLE IF NOT EXISTS "
            + StationItemColumns.TABLE_NAME + " ( "
            + StationItemColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StationItemColumns.STATION_ID + " INTEGER NOT NULL, "
            + StationItemColumns.STATUS + " INTEGER NOT NULL, "
            + StationItemColumns.NAME + " TEXT NOT NULL, "
            + StationItemColumns.BITRATE + " TEXT NOT NULL, "
            + StationItemColumns.STREAMURL + " TEXT NOT NULL, "
            + StationItemColumns.COUNTRY + " TEXT NOT NULL "
            + ", CONSTRAINT unique_external_id UNIQUE (station_id) ON CONFLICT IGNORE"
            + " );";

    public static final String SQL_CREATE_INDEX_STATION_ITEM_STATION_ID = "CREATE INDEX IDX_STATION_ITEM_STATION_ID "
            + " ON " + StationItemColumns.TABLE_NAME + " ( " + StationItemColumns.STATION_ID + " );";

    // @formatter:on

    public static AppSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static AppSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static AppSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new AppSQLiteOpenHelper(context);
    }

    private AppSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new AppSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static AppSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new AppSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private AppSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new AppSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_CATEGORY_ITEM);
        db.execSQL(SQL_CREATE_INDEX_CATEGORY_ITEM_CATEGORY_ID);
        db.execSQL(SQL_CREATE_TABLE_STATION_DATA);
        db.execSQL(SQL_CREATE_INDEX_STATION_DATA_STATION_ID);
        db.execSQL(SQL_CREATE_TABLE_STATION_ITEM);
        db.execSQL(SQL_CREATE_INDEX_STATION_ITEM_STATION_ID);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
