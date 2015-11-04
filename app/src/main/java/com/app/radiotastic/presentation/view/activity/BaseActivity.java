package com.app.radiotastic.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.app.radiotastic.App;
import com.app.radiotastic.internal.di.components.AppComponent;

/**
 * @author Tom Koptel
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
    }

    protected AppComponent getAppComponent() {
        return ((App) getApplicationContext()).getComponent();
    }
}
