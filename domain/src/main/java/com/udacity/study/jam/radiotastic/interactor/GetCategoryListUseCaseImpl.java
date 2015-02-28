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
import com.udacity.study.jam.radiotastic.executor.PostExecutionThread;
import com.udacity.study.jam.radiotastic.executor.ThreadExecutor;
import com.udacity.study.jam.radiotastic.repository.CategoryRepository;

import java.util.Collection;

public class GetCategoryListUseCaseImpl implements GetCategoryListUseCase {
    private final CategoryRepository categoryRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private Callback callback;

    public GetCategoryListUseCaseImpl(CategoryRepository categoryRepository,
                                      ThreadExecutor threadExecutor,
                                      PostExecutionThread postExecutionThread) {
        if (categoryRepository == null || threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.categoryRepository = categoryRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.callback = callback;
        threadExecutor.execute(this);
    }

    @Override
    public void run() {
        categoryRepository.getCategoryList(categoryListCallback);
    }

    private final CategoryRepository.CategoryListCallback categoryListCallback =
            new CategoryRepository.CategoryListCallback() {
                @Override
                public void onCategoryListLoaded(Collection<Category> categoriesCollection) {
                    notifyCategoryListSuccessfully(categoriesCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyCategoryListSuccessfully(final Collection<Category> categoryCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCategoryListLoaded(categoryCollection);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}
