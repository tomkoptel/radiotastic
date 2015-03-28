package com.udacity.study.jam.radiotastic.player;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface PlayerPref {
    String stationId();
}
