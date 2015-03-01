package com.udacity.study.jam.radiotastic.data.cache.db.stationdata;

import android.net.Uri;
import android.provider.BaseColumns;

import com.udacity.study.jam.radiotastic.data.cache.db.AppProvider;
import com.udacity.study.jam.radiotastic.data.cache.db.categoryitem.CategoryItemColumns;
import com.udacity.study.jam.radiotastic.data.cache.db.stationdata.StationDataColumns;
import com.udacity.study.jam.radiotastic.data.cache.db.stationitem.StationItemColumns;

/**
 * Overall available information regarding station. Provides more robust info regarding station.
 */
public class StationDataColumns implements BaseColumns {
    public static final String TABLE_NAME = "station_data";
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
     * Represents status of station. Either 1 UP or 0 DOWN
     */
    public static final String STATUS = "status";

    public static final String NAME = "name";

    public static final String WEBSITE = "website";

    public static final String STREAMURL = "streamurl";

    public static final String DESCRIPTION = "description";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            STATION_ID,
            STATUS,
            NAME,
            WEBSITE,
            STREAMURL,
            DESCRIPTION
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == STATION_ID || c.contains("." + STATION_ID)) return true;
            if (c == STATUS || c.contains("." + STATUS)) return true;
            if (c == NAME || c.contains("." + NAME)) return true;
            if (c == WEBSITE || c.contains("." + WEBSITE)) return true;
            if (c == STREAMURL || c.contains("." + STREAMURL)) return true;
            if (c == DESCRIPTION || c.contains("." + DESCRIPTION)) return true;
        }
        return false;
    }

}
