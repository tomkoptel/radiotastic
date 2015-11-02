package com.app.radiotastic.data.entity;

import com.app.radiotastic.data.db.station.StationColumns;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Tom Koptel
 */
@StorIOSQLiteType(table = StationColumns.TABLE_NAME)
public class DirbleStation {

    @Nullable
    @StorIOSQLiteColumn(name = StationColumns._ID, key = true)
    Long id;

    @NotNull
    @StorIOSQLiteColumn(name = StationColumns.STATION_ID)
    Long stationId;

    @NotNull
    @StorIOSQLiteColumn(name = StationColumns.NAME)
    String name;

    // We leave this for StoreIO needs
    public DirbleStation() {}

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public Long getStationId() {
        return stationId;
    }

    public void setStationId(@NotNull Long stationId) {
        this.stationId = stationId;
    }
}
