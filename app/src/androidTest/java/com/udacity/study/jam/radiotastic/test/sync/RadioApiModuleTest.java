/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.test.sync;

import android.os.Bundle;
import android.test.AndroidTestCase;

import com.udacity.study.jam.radiotastic.api.DirbleApiKey;
import com.udacity.study.jam.radiotastic.api.DirbleClient;
import com.udacity.study.jam.radiotastic.api.DirbleRadioApi;
import com.udacity.study.jam.radiotastic.api.FileRadioApi;
import com.udacity.study.jam.radiotastic.api.RadioApi;
import com.udacity.study.jam.radiotastic.di.module.RadioApiModule;
import com.udacity.study.jam.radiotastic.sync.SyncHelper;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class RadioApiModuleTest extends AndroidTestCase {
    @Mock
    DirbleApiKey apiKey;
    @Mock
    DirbleClient client;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
    }

    public void testModuleShouldProvideFileAPi() throws NoSuchFieldException {
        Bundle extras = new Bundle();
        extras.putInt(SyncHelper.SYNC_EXTRAS_FLAG, SyncHelper.FLAG_SYNC_CACHED);

        assertApiType(extras, FileRadioApi.class);
    }

    public void testModuleShouldProvideDirbleApi() throws NoSuchFieldException {
        Bundle extras = new Bundle();
        extras.putInt(SyncHelper.SYNC_EXTRAS_FLAG, SyncHelper.FLAG_SYNC_REMOTE);

        assertApiType(extras, DirbleRadioApi.class);
    }

    public void testModuleShouldProvideDirbleApiByDefault() throws NoSuchFieldException {
        Bundle extras = new Bundle();
        assertApiType(extras, DirbleRadioApi.class);
    }

    private void assertApiType(Bundle extras, Class<?> clazz) throws NoSuchFieldException {
        RadioApiModule module = new RadioApiModule(extras);
        RadioApi radioApi = module.provideRadioApi(getContext(), apiKey, client);
        assertThat(radioApi, is(instanceOf(clazz)));
    }
}
