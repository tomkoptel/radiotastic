package com.udacity.study.jam.radiotastic.util;


import com.orhanobut.logger.Logger;
import timber.log.Timber;

public final class DebugTree extends Timber.DebugTree {
    @Override
    public void v(String message, Object... args) {
        Logger.v(message);
    }

    @Override
    public void d(String message, Object... args) {
        Logger.d(message);
    }

    @Override
    public void i(String message, Object... args) {
        Logger.i(message);
    }


    @Override
    public void w(String message, Object... args) {
        Logger.w(message);
    }


    @Override
    public void e(String message, Object... args) {
        Logger.e(message);
    }

    @Override
    public void e(Throwable t, String message, Object... args) {
        Logger.e(new Exception(t));
    }
}
