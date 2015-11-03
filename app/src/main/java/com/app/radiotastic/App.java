package com.app.radiotastic;

import android.app.Application;

import com.app.radiotastic.internal.di.components.AppComponent;
import com.app.radiotastic.internal.di.components.DaggerAppComponent;
import com.app.radiotastic.internal.di.modules.AppModule;

public class App extends Application {
    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return mComponent;
    }

    public void setComponent(AppComponent component) {
        mComponent = component;
    }
}
