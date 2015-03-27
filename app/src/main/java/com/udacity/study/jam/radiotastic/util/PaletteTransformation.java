package com.udacity.study.jam.radiotastic.util;

import android.graphics.Bitmap;
import android.support.v4.util.Pools;
import android.support.v7.graphics.Palette;

import com.squareup.picasso.Transformation;

public class PaletteTransformation implements Transformation {
    private static final Pools.Pool<PaletteTransformation> POOL = new Pools.SynchronizedPool<>(5);

    public static PaletteTransformation getInstance() {
        PaletteTransformation instance = POOL.acquire();
        return instance != null ? instance : new PaletteTransformation();
    }

    private Palette palette;

    private PaletteTransformation() {}

    public Palette extractPaletteAndRelease() {
        Palette palette = this.palette;
        if (palette == null) {
            throw new IllegalStateException("Transformation was not run.");
        }
        this.palette = null;
        POOL.release(this);
        return palette;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        if (palette != null) {
            throw new IllegalStateException("Instances may only be used once.");
        }
        palette = Palette.generate(source);
        return source;
    }

    @Override
    public String key() {
        return "";
    }
}
