package com.app.radiotastic;

import android.app.Application;

import com.app.radiotastic.internal.di.components.AppComponent;
import com.app.radiotastic.internal.di.components.DaggerAppComponent;
import com.app.radiotastic.internal.di.modules.AppModule;

public final class App extends Application {
    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppComponent getComponent() {
        if (mComponent == null) {
            synchronized (App.class) {
                if (mComponent == null) {
                    mComponent = createDefaultComponent();
                }
            }
        }

        return mComponent;
    }

    public void setComponent(AppComponent component) {
        mComponent = component;
    }

    private AppComponent createDefaultComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
