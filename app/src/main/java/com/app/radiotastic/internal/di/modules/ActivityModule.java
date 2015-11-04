package com.app.radiotastic.internal.di.modules;

import android.app.Activity;

import com.app.radiotastic.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author Tom Koptel
 */
@Module
public final class ActivityModule {
    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    Activity providesActivity() {
        return mActivity;
    }
}
