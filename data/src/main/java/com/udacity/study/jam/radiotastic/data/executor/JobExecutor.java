/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.udacity.study.jam.radiotastic.data.executor;


import com.udacity.study.jam.radiotastic.executor.ThreadExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Decorated {@link java.util.concurrent.ThreadPoolExecutor} Singleton class based on
 * 'Initialization on Demand Holder' pattern.
 */
public class JobExecutor implements ThreadExecutor {

  private static class LazyHolder {
    private static final JobExecutor INSTANCE = new JobExecutor();
  }

  public static JobExecutor getInstance() {
    return LazyHolder.INSTANCE;
  }

  private static final int INITIAL_POOL_SIZE = 3;
  private static final int MAX_POOL_SIZE = 5;

  // Sets the amount of time an idle thread waits before terminating
  private static final int KEEP_ALIVE_TIME = 10;

  // Sets the Time Unit to seconds
  private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

  private final BlockingQueue<Runnable> workQueue;

  private final ThreadPoolExecutor threadPoolExecutor;

  private final ThreadFactory threadFactory;

  private JobExecutor() {
    this.workQueue = new LinkedBlockingQueue<>();
    this.threadFactory = new JobThreadFactory();
    this.threadPoolExecutor = new ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
        KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, this.workQueue, this.threadFactory);
  }

  /**
   * {@inheritDoc}
   *
   * @param runnable The class that implements {@link Runnable} interface.
   */
  @Override public void execute(Runnable runnable) {
    if (runnable == null) {
      throw new IllegalArgumentException("Runnable to execute cannot be null");
    }
    this.threadPoolExecutor.execute(runnable);
  }

  private static class JobThreadFactory implements ThreadFactory {
    private static final String THREAD_NAME = "android_";
    private int counter = 0;

    @Override public Thread newThread(Runnable runnable) {
      return new Thread(runnable, THREAD_NAME + counter);
    }
  }
}