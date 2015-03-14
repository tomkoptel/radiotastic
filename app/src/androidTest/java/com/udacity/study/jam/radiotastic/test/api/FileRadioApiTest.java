/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.test.api;

import android.test.AndroidTestCase;

import com.udacity.study.jam.radiotastic.CategoryItem;
import com.udacity.study.jam.radiotastic.api.FileRadioApi;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class FileRadioApiTest extends AndroidTestCase {

    public void testApiShouldParseRawFile() {
        FileRadioApi radioApi = new FileRadioApi(getContext());
        Collection<CategoryItem> items = radioApi.listPrimaryCategories();
        assertThat(items, notNullValue());
    }

}
