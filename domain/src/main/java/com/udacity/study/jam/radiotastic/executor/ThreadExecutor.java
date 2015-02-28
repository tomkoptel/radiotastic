/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.udacity.study.jam.radiotastic.executor;

import com.udacity.study.jam.radiotastic.interactor.Interactor;
/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the {@link Interactor} out of the UI thread.
 *
 * Use this class to execute an {@link Interactor}.
 */
public interface ThreadExecutor {
  /**
   * Executes a {@link Runnable}.
   *
   * @param runnable The class that implements {@link Runnable} interface.
   */
  void execute(final Runnable runnable);
}
