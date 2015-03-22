/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.adapter.easy;

import android.content.Context;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class BaseEasyViewHolderFactory {

    protected Context context;

    private Map<Class, Class<? extends EasyViewHolder>> boundViewHolders = new HashMap<>();

    public BaseEasyViewHolderFactory(Context context) {
        this.context = context;
    }

    public EasyViewHolder create(Class valueClass, ViewGroup parent) {
        try {
            Class<? extends EasyViewHolder> easyViewHolderClass = boundViewHolders.get(valueClass);
            Constructor<? extends EasyViewHolder> constructor = easyViewHolderClass.getDeclaredConstructor(Context.class, ViewGroup.class);
            return constructor.newInstance(context, parent);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    void bind(Class valueClass, Class<? extends EasyViewHolder> viewHolder) {
        boundViewHolders.put(valueClass, viewHolder);
    }

}
