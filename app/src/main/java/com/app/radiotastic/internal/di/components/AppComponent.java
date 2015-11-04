package com.app.radiotastic.internal.di.components;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.radiotastic.domain.repository.StationRepository;
import com.app.radiotastic.internal.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Tom Koptel
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Context context();
    SQLiteOpenHelper openHelper();
    StationRepository stationRepository();
}
