/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.udacity.study.jam.radiotastic.interactor;

/**
 * Common interface for an Interactor {@link Runnable} declared in the application.
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each Interactor implementation will return the result using a Callback that should
 * be executed in the UI thread.
 */
public interface Interactor extends Runnable {
  /**
   * Everything inside this method will be executed asynchronously.
   */
  void run();
}
