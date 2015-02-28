/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.interactor;

import com.udacity.study.jam.radiotastic.Station;
import com.udacity.study.jam.radiotastic.exception.ErrorBundle;
import com.udacity.study.jam.radiotastic.executor.PostExecutionThread;
import com.udacity.study.jam.radiotastic.executor.ThreadExecutor;
import com.udacity.study.jam.radiotastic.repository.StationRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Collection;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetStationListByCategoryUseCaseTest {
    private final String FAKE_CATEGORY_ID = "100";
    private GetStationListByCategoryUseCase getStationListByCategoryUseCase;
    
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private StationRepository mockStationRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        getStationListByCategoryUseCase = new GetStationListByCategoryUseCaseImpl(mockStationRepository,
                mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void testGetStationListByCategoryUseCaseExecution() {
        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));

        GetStationListByCategoryUseCase.Callback callback
                = mock(GetStationListByCategoryUseCase.Callback.class);

        getStationListByCategoryUseCase.execute(FAKE_CATEGORY_ID, callback);

        verify(mockThreadExecutor).execute(any(Interactor.class));
        verifyNoMoreInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockStationRepository);
        verifyZeroInteractions(mockPostExecutionThread);
    }

    @Test
    public void testGetStationListByCategoryUseCaseInteractorRun() {
        GetStationListByCategoryUseCase.Callback mockCallback =
                mock(GetStationListByCategoryUseCase.Callback.class);

        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
        doNothing().when(mockStationRepository).getStationListByCategory(anyString(),
                any(StationRepository.StationListCallback.class));

        getStationListByCategoryUseCase.execute(FAKE_CATEGORY_ID, mockCallback);
        getStationListByCategoryUseCase.run();

        verify(mockStationRepository).getStationListByCategory(anyString(),
                any(StationRepository.StationListCallback.class));
        verify(mockThreadExecutor).execute(any(Interactor.class));
        verifyNoMoreInteractions(mockStationRepository);
        verifyNoMoreInteractions(mockThreadExecutor);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetStationListByCategoryUseCaseCallbackSuccessful() {
        final GetStationListByCategoryUseCase.Callback mockCallback =
                mock(GetStationListByCategoryUseCase.Callback.class);
        final Collection<Station> mockStationsList =
                (Collection<Station>) mock(Collection.class);

        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
        doAnswer(new Answer() {
            @Override public Object answer(InvocationOnMock invocation) throws Throwable {
                ((StationRepository.StationListCallback) invocation.getArguments()[1]).onStationListLoaded(
                        mockStationsList);
                return null;
            }
        }).when(mockStationRepository)
                .getStationListByCategory(anyString(), any(StationRepository.StationListCallback.class));

        getStationListByCategoryUseCase.execute(FAKE_CATEGORY_ID, mockCallback);
        getStationListByCategoryUseCase.run();

        verify(mockPostExecutionThread).post(any(Runnable.class));
        verifyNoMoreInteractions(mockCallback);
        verifyZeroInteractions(mockStationsList);
    }

    @Test
    public void testGetStationListByCategoryUseCaseCallbackError() {
        final GetStationListByCategoryUseCase.Callback mockCallback =
                mock(GetStationListByCategoryUseCase.Callback.class);
        final ErrorBundle mockErrorBundle = mock(ErrorBundle.class);

        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
        doAnswer(new Answer() {
            @Override public Object answer(InvocationOnMock invocation) throws Throwable {
                ((StationRepository.StationListCallback) invocation.getArguments()[1]).onError(mockErrorBundle);
                return null;
            }
        }).when(mockStationRepository)
                .getStationListByCategory(anyString(),
                        any(StationRepository.StationListCallback.class));

        getStationListByCategoryUseCase.execute(FAKE_CATEGORY_ID, mockCallback);
        getStationListByCategoryUseCase.run();

        verify(mockPostExecutionThread).post(any(Runnable.class));
        verifyNoMoreInteractions(mockCallback);
        verifyZeroInteractions(mockErrorBundle);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteGetStationListUseCaseNullCategoryId() {
        final GetStationListByCategoryUseCase.Callback mockCallback =
                mock(GetStationListByCategoryUseCase.Callback.class);
        getStationListByCategoryUseCase.execute(null, mockCallback);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteGetStationListUseCaseEmptyCategoryId() {
        final GetStationListByCategoryUseCase.Callback mockCallback =
                mock(GetStationListByCategoryUseCase.Callback.class);
        getStationListByCategoryUseCase.execute("", mockCallback);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteGetStationListUseCaseNullCallback() {
        getStationListByCategoryUseCase.execute(FAKE_CATEGORY_ID, null);
    }

}
