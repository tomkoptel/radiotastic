/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.interactor;

import com.udacity.study.jam.radiotastic.Category;
import com.udacity.study.jam.radiotastic.exception.ErrorBundle;

import java.util.Collection;

public interface GetCategoryListUseCase extends Interactor {

    interface Callback {
        void onCategoryListLoaded(Collection<Category> usersCollection);
        void onError(ErrorBundle errorBundle);
    }

    void execute(Callback callback);
}
