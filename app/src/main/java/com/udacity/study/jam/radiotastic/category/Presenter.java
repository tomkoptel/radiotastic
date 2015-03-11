/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.udacity.study.jam.radiotastic.category;

/**
 * Abstract presenter to work as base for every presenter created in the application. This
 * presenter
 * declares some methods to attach the fragment/activity lifecycle.
 *
 * @author Pedro Vicente Gómez Sánchez
 */
public abstract class Presenter {

  /**
   * Called when the presenter is initialized, this method represents the start of the presenter
   * lifecycle.
   */
  public abstract void initialize();

  /**
   * Called when the presenter is resumed. After the initialization and when the presenter comes
   * from a pause state.
   */
  public abstract void resume();

  /**
   * Called when the presenter is paused.
   */
  public abstract void pause();
}
