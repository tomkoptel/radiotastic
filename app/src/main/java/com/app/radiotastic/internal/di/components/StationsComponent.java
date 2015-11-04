package com.app.radiotastic.internal.di.components;

import com.app.radiotastic.internal.di.PerActivity;
import com.app.radiotastic.internal.di.modules.ActivityModule;
import com.app.radiotastic.internal.di.modules.StationModule;
import com.app.radiotastic.presentation.view.fragment.StationListFragment;

import dagger.Component;

/**
 * @author Tom Koptel
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, StationModule.class})
public interface StationsComponent extends ActivityComponent {
    void inject(StationListFragment stationListFragment);
}
