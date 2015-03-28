package com.udacity.study.jam.radiotastic.ui;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface UiPref {
    @DefaultString("name")
    String sortOption();

    @DefaultString("asc")
    String sortOrder();
}
