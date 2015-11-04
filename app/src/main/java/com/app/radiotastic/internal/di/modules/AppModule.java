package com.app.radiotastic.internal.di.modules;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.radiotastic.data.cache.StationCache;
import com.app.radiotastic.data.cache.StationCacheImpl;
import com.app.radiotastic.data.db.AppSQLiteOpenHelper;
import com.app.radiotastic.data.entity.DirbleStation;
import com.app.radiotastic.data.entity.DirbleStationStorIOSQLiteDeleteResolver;
import com.app.radiotastic.data.entity.DirbleStationStorIOSQLiteGetResolver;
import com.app.radiotastic.data.entity.DirbleStationStorIOSQLitePutResolver;
import com.app.radiotastic.data.repository.StationDataRepository;
import com.app.radiotastic.domain.repository.StationRepository;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Tom Koptel
 */
@Module
public final class AppModule {
    private final Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    SQLiteOpenHelper providesSqliteOpenHelper(Context context) {
        return AppSQLiteOpenHelper.newInstance(context);
    }

    @Provides
    @Singleton
    StorIOSQLite provideStoreIO(SQLiteOpenHelper sqLiteOpenHelper) {
        SQLiteTypeMapping<DirbleStation> typeMapping = SQLiteTypeMapping.<DirbleStation>builder()
                .putResolver(new DirbleStationStorIOSQLitePutResolver())
                .getResolver(new DirbleStationStorIOSQLiteGetResolver())
                .deleteResolver(new DirbleStationStorIOSQLiteDeleteResolver())
                .build();

        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(sqLiteOpenHelper)
                .addTypeMapping(DirbleStation.class, typeMapping)
                .build();
    }

    @Provides
    @Singleton
    StationCache provideStationCache(StationCacheImpl stationCache) {
        return stationCache;
    }

    @Provides
    @Singleton
    StationRepository provideStationRepository(StationDataRepository stationDataRepository) {
        return stationDataRepository;
    }
}
