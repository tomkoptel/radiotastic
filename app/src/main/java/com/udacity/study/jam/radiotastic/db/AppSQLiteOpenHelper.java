package com.udacity.study.jam.radiotastic.db;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.udacity.study.jam.radiotastic.BuildConfig;
import com.udacity.study.jam.radiotastic.db.category.CategoryColumns;
import com.udacity.study.jam.radiotastic.db.station.StationColumns;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataColumns;

public class AppSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = AppSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "radiotastic.db";
    private static final int DATABASE_VERSION = 1;
    private static AppSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final AppSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_CATEGORY = "CREATE TABLE IF NOT EXISTS "
            + CategoryColumns.TABLE_NAME + " ( "
            + CategoryColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CategoryColumns.CATEGORY_ID + " INTEGER NOT NULL, "
            + CategoryColumns.NAME + " TEXT NOT NULL, "
            + CategoryColumns.DESCRIPTION + " TEXT "
            + ", CONSTRAINT unique_external_id UNIQUE (category_id) ON CONFLICT IGNORE"
            + ", CONSTRAINT unique_name UNIQUE (name) ON CONFLICT IGNORE"
            + " );";

    public static final String SQL_CREATE_INDEX_CATEGORY_CATEGORY_ID = "CREATE INDEX IDX_CATEGORY_CATEGORY_ID "
            + " ON " + CategoryColumns.TABLE_NAME + " ( " + CategoryColumns.CATEGORY_ID + " );";

    public static final String SQL_CREATE_TABLE_STATION = "CREATE TABLE IF NOT EXISTS "
            + StationColumns.TABLE_NAME + " ( "
            + StationColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StationColumns.STATION_ID + " INTEGER NOT NULL, "
            + StationColumns.CATEGORY_ID + " INTEGER NOT NULL, "
            + StationColumns.STATUS + " INTEGER NOT NULL, "
            + StationColumns.NAME + " TEXT NOT NULL, "
            + StationColumns.BITRATE + " INTEGER NOT NULL, "
            + StationColumns.STREAMURL + " TEXT NOT NULL, "
            + StationColumns.COUNTRY + " TEXT NOT NULL, "
            + StationColumns.WEBSITE + " TEXT, "
            + StationColumns.DESCRIPTION + " TEXT "
            + ", CONSTRAINT unique_station_category_id_combination UNIQUE (station_id, category_id) ON CONFLICT IGNORE"
            + ", CONSTRAINT not_empty_station_name CHECK(name <> '') ON CONFLICT IGNORE"
            + " );";

    public static final String SQL_CREATE_INDEX_STATION_STATION_ID = "CREATE INDEX IDX_STATION_STATION_ID "
            + " ON " + StationColumns.TABLE_NAME + " ( " + StationColumns.STATION_ID + " );";

    public static final String SQL_CREATE_INDEX_STATION_CATEGORY_ID = "CREATE INDEX IDX_STATION_CATEGORY_ID "
            + " ON " + StationColumns.TABLE_NAME + " ( " + StationColumns.CATEGORY_ID + " );";

    public static final String SQL_CREATE_TABLE_STATION_META_DATA = "CREATE TABLE IF NOT EXISTS "
            + StationMetaDataColumns.TABLE_NAME + " ( "
            + StationMetaDataColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StationMetaDataColumns.STATION_ID + " INTEGER NOT NULL, "
            + StationMetaDataColumns.META + " TEXT, "
            + StationMetaDataColumns.CREATED_AT + " INTEGER NOT NULL "
            + ", CONSTRAINT unique_station_category_id_combination UNIQUE (station_id) ON CONFLICT IGNORE"
            + " );";

    public static final String SQL_CREATE_INDEX_STATION_META_DATA_STATION_ID = "CREATE INDEX IDX_STATION_META_DATA_STATION_ID "
            + " ON " + StationMetaDataColumns.TABLE_NAME + " ( " + StationMetaDataColumns.STATION_ID + " );";

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
        db.execSQL(SQL_CREATE_TABLE_CATEGORY);
        db.execSQL(SQL_CREATE_INDEX_CATEGORY_CATEGORY_ID);
        db.execSQL(SQL_CREATE_TABLE_STATION);
        db.execSQL(SQL_CREATE_INDEX_STATION_STATION_ID);
        db.execSQL(SQL_CREATE_INDEX_STATION_CATEGORY_ID);
        db.execSQL(SQL_CREATE_TABLE_STATION_META_DATA);
        db.execSQL(SQL_CREATE_INDEX_STATION_META_DATA_STATION_ID);
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
