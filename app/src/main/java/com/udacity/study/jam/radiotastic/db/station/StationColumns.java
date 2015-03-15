package com.udacity.study.jam.radiotastic.db.station;

import android.net.Uri;
import android.provider.BaseColumns;

import com.udacity.study.jam.radiotastic.db.AppProvider;
import com.udacity.study.jam.radiotastic.db.category.CategoryColumns;
import com.udacity.study.jam.radiotastic.db.station.StationColumns;
import com.udacity.study.jam.radiotastic.db.stationmetadata.StationMetaDataColumns;

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

    /**
     * Represents id of asscociated category with specific station.
     */
    public static final String CATEGORY_ID = "category_id";

    /**
     * Represents status of station. Either 1 UP or 0 DOWN
     */
    public static final String STATUS = "status";

    public static final String NAME = "name";

    public static final String BITRATE = "bitrate";

    public static final String STREAMURL = "streamurl";

    public static final String COUNTRY = "country";

    public static final String WEBSITE = "website";

    public static final String DESCRIPTION = "description";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            STATION_ID,
            CATEGORY_ID,
            STATUS,
            NAME,
            BITRATE,
            STREAMURL,
            COUNTRY,
            WEBSITE,
            DESCRIPTION
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(STATION_ID) || c.contains("." + STATION_ID)) return true;
            if (c.equals(CATEGORY_ID) || c.contains("." + CATEGORY_ID)) return true;
            if (c.equals(STATUS) || c.contains("." + STATUS)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(BITRATE) || c.contains("." + BITRATE)) return true;
            if (c.equals(STREAMURL) || c.contains("." + STREAMURL)) return true;
            if (c.equals(COUNTRY) || c.contains("." + COUNTRY)) return true;
            if (c.equals(WEBSITE) || c.contains("." + WEBSITE)) return true;
            if (c.equals(DESCRIPTION) || c.contains("." + DESCRIPTION)) return true;
        }
        return false;
    }

}
