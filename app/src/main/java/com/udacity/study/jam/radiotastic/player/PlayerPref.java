package com.udacity.study.jam.radiotastic.player;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface PlayerPref {
    String stationId();
}
