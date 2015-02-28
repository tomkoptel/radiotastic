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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Collection;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetCategoryListUseCaseTest {

    private GetCategoryListUseCase getCategoryListUseCase;

    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private CategoryRepository mockCategoryRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        getCategoryListUseCase = new GetCategoryListUseCaseImpl(mockCategoryRepository,
                mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void testGetCategoryListUseCaseExecution() {
        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));

        GetCategoryListUseCase.Callback mockGetCategoryListCallback =
                mock(GetCategoryListUseCase.Callback.class);

        getCategoryListUseCase.execute(mockGetCategoryListCallback);

        verify(mockThreadExecutor).execute(any(Interactor.class));
        verifyNoMoreInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockCategoryRepository);
    }

    @Test
    public void testGetCategoryListUseCaseInteractorRun() {
        GetCategoryListUseCase.Callback mockGetCategoryListCallback =
                mock(GetCategoryListUseCase.Callback.class);
        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
        doNothing().when(mockCategoryRepository)
                .getCategoryList(any(CategoryRepository.CategoryListCallback.class));

        getCategoryListUseCase.execute(mockGetCategoryListCallback);
        getCategoryListUseCase.run();

        verify(mockCategoryRepository).getCategoryList(any(CategoryRepository.CategoryListCallback.class));
        verify(mockThreadExecutor).execute(any(Interactor.class));
        verifyNoMoreInteractions(mockCategoryRepository);
        verifyNoMoreInteractions(mockThreadExecutor);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCategoryListUseCaseCallbackSuccessful() {
        GetCategoryListUseCase.Callback mockGetCategoryListCallback =
                mock(GetCategoryListUseCase.Callback.class);
        final Collection<Category> mockResponseCategories = (Collection<Category>) mock(Collection.class);

        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((CategoryRepository.CategoryListCallback) invocation.getArguments()[0])
                        .onCategoryListLoaded(mockResponseCategories);
                return null;
            }
        }).when(mockCategoryRepository).getCategoryList(any(CategoryRepository.CategoryListCallback.class));

        getCategoryListUseCase.execute(mockGetCategoryListCallback);
        getCategoryListUseCase.run();

        verify(mockPostExecutionThread).post(any(Runnable.class));
        verifyNoMoreInteractions(mockGetCategoryListCallback);
        verifyZeroInteractions(mockResponseCategories);
    }

    @Test
    public void testCategoryListUseCaseCallbackError() {
        final GetCategoryListUseCase.Callback mockGetCategoryListCallback =
                mock(GetCategoryListUseCase.Callback.class);
        final ErrorBundle mockErrorBundle = mock(ErrorBundle.class);

        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
        doAnswer(new Answer() {
            @Override public Object answer(InvocationOnMock invocation) throws Throwable {
                ((CategoryRepository.CategoryListCallback) invocation.getArguments()[0]).onError(mockErrorBundle);
                return null;
            }
        }).when(mockCategoryRepository).getCategoryList(any(CategoryRepository.CategoryListCallback.class));

        getCategoryListUseCase.execute(mockGetCategoryListCallback);
        getCategoryListUseCase.run();

        verify(mockPostExecutionThread).post(any(Runnable.class));
        verifyNoMoreInteractions(mockGetCategoryListCallback);
        verifyZeroInteractions(mockErrorBundle);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteUserListUseCaseNullParameter() {
        getCategoryListUseCase.execute(null);
    }


}
