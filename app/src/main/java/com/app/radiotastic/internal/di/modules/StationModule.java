package com.app.radiotastic.internal.di.modules;

import com.app.radiotastic.domain.interactor.GetStationList;
import com.app.radiotastic.domain.interactor.UseCase;
import com.app.radiotastic.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Provides;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public final class StationModule {

    @Provides
    @PerActivity
    @Named("stationList")
    UseCase provideGetStationListUseCase(GetStationList getStationList) {
        return getStationList;
    }
}
