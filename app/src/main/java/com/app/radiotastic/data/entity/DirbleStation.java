package com.app.radiotastic.data.entity;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Tom Koptel
 */
@StorIOSQLiteType(table = "stations")
public class DirbleStation {

    @Nullable
    @StorIOSQLiteColumn(name = "_id", key = true)
    Long id;

    @NotNull
    @StorIOSQLiteColumn(name = "name")
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
}
