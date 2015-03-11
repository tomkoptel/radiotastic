/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.di.component;

import com.udacity.study.jam.radiotastic.ui.activity.MainActivity;
import com.udacity.study.jam.radiotastic.ui.fragment.CategoryListFragment;
import com.udacity.study.jam.radiotastic.ui.presenter.CategoryPresenter;
import com.udacity.study.jam.radiotastic.ui.presenter.StationPresenter;

public interface AppGraph {
    void inject(MainActivity activity);
    void inject(CategoryPresenter presenter);
    void inject(StationPresenter presenter);
}
