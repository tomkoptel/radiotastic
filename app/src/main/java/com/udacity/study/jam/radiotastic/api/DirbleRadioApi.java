/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.api;

import com.udacity.study.jam.radiotastic.CategoryItem;
import com.udacity.study.jam.radiotastic.StationItem;

import java.util.Collection;

public class DirbleRadioApi implements RadioApi {
    private final DirbleApiKey apiKey;
    private final DirbleClient dirbleClient;

    public DirbleRadioApi(DirbleApiKey apiKey, DirbleClient dirbleClient) {
        this.apiKey = apiKey;
        this.dirbleClient = dirbleClient;
    }

    @Override
    public Collection<CategoryItem> listPrimaryCategories() {
        return dirbleClient.listPrimaryCategories(apiKey.get());
    }

    @Override
    public Collection<StationItem> listStations(String categoryId) {
        return dirbleClient.listStations(apiKey.get(), categoryId);
    }
}
