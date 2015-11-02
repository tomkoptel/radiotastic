package com.app.radiotastic.data.db.station;

import android.net.Uri;
import android.provider.BaseColumns;

import com.app.radiotastic.data.db.AppProvider;

/**
 * General station info. This is basic set of data enough for client to playback.
 */
public class StationColumns implements BaseColumns {
    public static final String TABLE_NAME = "station";
    public static final Uri CONTENT_URI = Uri.parse(AppProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Represents id of object which resides on backend.
     */
    public static final String STATION_ID = "station_id";

    public static final String NAME = "name";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            STATION_ID,
            NAME
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(STATION_ID) || c.contains("." + STATION_ID)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
        }
        return false;
    }

}
