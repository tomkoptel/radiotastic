package com.app.radiotastic.internal.di.modules;

import com.app.radiotastic.domain.interactor.GetStationList;
import com.app.radiotastic.domain.interactor.UseCase;
import com.app.radiotastic.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author Tom Koptel
 */
@Module
public final class StationModule {

    @Provides
    @PerActivity
    @Named("stationList")
    UseCase provideGetStationListUseCase(GetStationList getStationList) {
        return getStationList;
    }
}
