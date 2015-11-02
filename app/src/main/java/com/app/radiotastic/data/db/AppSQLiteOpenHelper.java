package com.app.radiotastic.data.db;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.app.radiotastic.BuildConfig;
import com.app.radiotastic.data.db.station.StationColumns;

public class AppSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = AppSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "radiotastic.db";
    private static final int DATABASE_VERSION = 1;
    private static AppSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final AppSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_STATION = "CREATE TABLE IF NOT EXISTS "
            + StationColumns.TABLE_NAME + " ( "
            + StationColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StationColumns.STATION_ID + " INTEGER NOT NULL, "
            + StationColumns.NAME + " TEXT NOT NULL "
            + ", CONSTRAINT not_empty_station_name CHECK(name <> '') ON CONFLICT IGNORE"
            + " );";

    public static final String SQL_CREATE_INDEX_STATION_STATION_ID = "CREATE INDEX IDX_STATION_STATION_ID "
            + " ON " + StationColumns.TABLE_NAME + " ( " + StationColumns.STATION_ID + " );";

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
        db.execSQL(SQL_CREATE_TABLE_STATION);
        db.execSQL(SQL_CREATE_INDEX_STATION_STATION_ID);
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
