package com.udacity.study.jam.radiotastic.db.categoryitem;

import android.net.Uri;
import android.provider.BaseColumns;

import com.udacity.study.jam.radiotastic.db.AppProvider;
import com.udacity.study.jam.radiotastic.db.categoryitem.CategoryItemColumns;

/**
 * A category being which represents group of stations.
 */
public class CategoryItemColumns implements BaseColumns {
    public static final String TABLE_NAME = "category_item";
    public static final Uri CONTENT_URI = Uri.parse(AppProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Represents id of object which resides on backend.
     */
    public static final String EXTERNAL_ID = "external_id";

    /**
     * Name of category
     */
    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            EXTERNAL_ID,
            NAME,
            DESCRIPTION
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == EXTERNAL_ID || c.contains("." + EXTERNAL_ID)) return true;
            if (c == NAME || c.contains("." + NAME)) return true;
            if (c == DESCRIPTION || c.contains("." + DESCRIPTION)) return true;
        }
        return false;
    }

}
