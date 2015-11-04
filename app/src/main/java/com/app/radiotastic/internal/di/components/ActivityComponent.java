package com.app.radiotastic.internal.di.components;

import android.app.Activity;

import com.app.radiotastic.internal.di.PerActivity;
import com.app.radiotastic.internal.di.modules.ActivityModule;

import dagger.Component;

/**
 * @author Tom Koptel
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
