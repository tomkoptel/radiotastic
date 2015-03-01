/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.presenter;

import com.udacity.study.jam.radiotastic.Category;
import com.udacity.study.jam.radiotastic.exception.ErrorBundle;
import com.udacity.study.jam.radiotastic.interactor.GetCategoryListUseCase;
import com.udacity.study.jam.radiotastic.model.CategoryModel;
import com.udacity.study.jam.radiotastic.view.CategoryListView;

import java.util.Collection;

public class CategoryListPresenter implements Presenter {

    private final CategoryListView categoryListView;
    private final GetCategoryListUseCase getCategoryListUseCase;

    public CategoryListPresenter(CategoryListView categoryListView,
                                 GetCategoryListUseCase getCategoryListUseCase) {
        this.categoryListView = categoryListView;
        this.getCategoryListUseCase = getCategoryListUseCase;
    }

    public void onCategoryClicked(CategoryModel categoryModel) {
        categoryListView.viewUser(categoryModel);
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    public void init() {
//        getCategoryListUseCase.execute();
    }

    private final GetCategoryListUseCase.Callback categoryListCallback =
            new GetCategoryListUseCase.Callback() {
                @Override
                public void onCategoryListLoaded(Collection<Category> usersCollection) {

                }

                @Override
                public void onError(ErrorBundle errorBundle) {

                }
            };
}
