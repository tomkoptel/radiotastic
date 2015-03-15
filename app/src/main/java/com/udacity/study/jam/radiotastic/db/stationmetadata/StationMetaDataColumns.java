package com.udacity.study.jam.radiotastic.db.stationmetadata;

import android.net.Uri;
import android.provider.BaseColumns;

import com.udacity.study.jam.radiotastic.db.AppProvider;
import com.udacity.study.jam.radiotastic.db.category.CategoryColumns;
import com.udacity.study.jam.radiotastic.db.station.StationColumns;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataColumns;

/**
 * General station info. This is basic set of data enough for client to playback.
 */
public class StationMetaDataColumns implements BaseColumns {
    public static final String TABLE_NAME = "station_meta_data";
    public static final Uri CONTENT_URI = Uri.parse(AppProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Represents id of object which resides on backend.
     */
    public static final String STATION_ID = "station_id";

    public static final String META = "meta";

    public static final String CREATED_AT = "created_at";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            STATION_ID,
            META,
            CREATED_AT
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(STATION_ID) || c.contains("." + STATION_ID)) return true;
            if (c.equals(META) || c.contains("." + META)) return true;
            if (c.equals(CREATED_AT) || c.contains("." + CREATED_AT)) return true;
        }
        return false;
    }

}
