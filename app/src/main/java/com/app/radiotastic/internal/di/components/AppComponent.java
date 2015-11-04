package com.app.radiotastic.internal.di.components;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.radiotastic.domain.executor.PostExecutionThread;
import com.app.radiotastic.domain.executor.PreExecutionThread;
import com.app.radiotastic.domain.repository.StationRepository;
import com.app.radiotastic.internal.di.modules.AppModule;
import com.app.radiotastic.presentation.view.activity.BaseActivity;

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
    PreExecutionThread preExecutionThread();
    PostExecutionThread postExecutionThread();

    void inject(BaseActivity baseActivity);
}
