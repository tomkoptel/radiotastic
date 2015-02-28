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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetStationDetailsUseCaseTest {
    private final String FAKE_STATION_ID = "100";
    private GetStationDetailsUseCase getStationDetailsUseCase;

    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private StationRepository mockStationRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        getStationDetailsUseCase = new GetStationDetailsUseCaseImpl(mockStationRepository,
                mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void testGetStationDetailsUseCaseExecution() {
        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));

        GetStationDetailsUseCase.Callback callback
                = mock(GetStationDetailsUseCase.Callback.class);

        getStationDetailsUseCase.execute(FAKE_STATION_ID, callback);

        verify(mockThreadExecutor).execute(any(Interactor.class));
        verifyNoMoreInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockStationRepository);
        verifyZeroInteractions(mockPostExecutionThread);
    }

    @Test
    public void testGetStationDetailsUseCaseInteractorRun() {
        GetStationDetailsUseCase.Callback mockCallback =
                mock(GetStationDetailsUseCase.Callback.class);

        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
        doNothing().when(mockStationRepository).getStationById(anyString(),
                any(StationRepository.StationDetailsCallback.class));

        getStationDetailsUseCase.execute(FAKE_STATION_ID, mockCallback);
        getStationDetailsUseCase.run();

        verify(mockStationRepository).getStationById(anyString(),
                any(StationRepository.StationDetailsCallback.class));
        verify(mockThreadExecutor).execute(any(Interactor.class));
        verifyNoMoreInteractions(mockStationRepository);
        verifyNoMoreInteractions(mockThreadExecutor);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetStationDetailsUseCaseCallbackSuccessful() {
        final GetStationDetailsUseCase.Callback mockCallback =
                mock(GetStationDetailsUseCase.Callback.class);
        final Station mockStation = mock(Station.class);

        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
        doAnswer(new Answer() {
            @Override public Object answer(InvocationOnMock invocation) throws Throwable {
                ((StationRepository.StationDetailsCallback) invocation.getArguments()[1]).onStationLoaded(
                        mockStation);
                return null;
            }
        }).when(mockStationRepository)
                .getStationById(anyString(), any(StationRepository.StationDetailsCallback.class));

        getStationDetailsUseCase.execute(FAKE_STATION_ID, mockCallback);
        getStationDetailsUseCase.run();

        verify(mockPostExecutionThread).post(any(Runnable.class));
        verifyNoMoreInteractions(mockCallback);
        verifyZeroInteractions(mockStation);
    }

    @Test
    public void testStationListByCategoryUseCaseCallbackError() {
        final GetStationDetailsUseCase.Callback mockCallback =
                mock(GetStationDetailsUseCase.Callback.class);
        final ErrorBundle mockErrorBundle = mock(ErrorBundle.class);

        doNothing().when(mockThreadExecutor).execute(any(Interactor.class));
        doAnswer(new Answer() {
            @Override public Object answer(InvocationOnMock invocation) throws Throwable {
                ((StationRepository.StationDetailsCallback) invocation.getArguments()[1]).onError(mockErrorBundle);
                return null;
            }
        }).when(mockStationRepository)
                .getStationById(anyString(),
                        any(StationRepository.StationDetailsCallback.class));

        getStationDetailsUseCase.execute(FAKE_STATION_ID, mockCallback);
        getStationDetailsUseCase.run();

        verify(mockPostExecutionThread).post(any(Runnable.class));
        verifyNoMoreInteractions(mockCallback);
        verifyZeroInteractions(mockErrorBundle);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteGetStationDetailsUseCaseNullCategoryId() {
        final GetStationDetailsUseCase.Callback mockCallback =
                mock(GetStationDetailsUseCase.Callback.class);
        getStationDetailsUseCase.execute(null, mockCallback);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteGetStationDetailsUseCaseEmptyCategoryId() {
        final GetStationDetailsUseCase.Callback mockCallback =
                mock(GetStationDetailsUseCase.Callback.class);
        getStationDetailsUseCase.execute("", mockCallback);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteGetStationDetailsUseCaseNullCallback() {
        getStationDetailsUseCase.execute(FAKE_STATION_ID, null);
    }
}
