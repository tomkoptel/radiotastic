/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.adapter.holder;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.db.station.StationCursor;
import com.udacity.study.jam.radiotastic.ui.adapter.easy.EasyViewHolder;

public class StationViewHolder extends EasyViewHolder<Cursor> {
    private final TextView labelTextView;
    private final TextView descTextView;

    public StationViewHolder(ViewGroup parent) {
        super(parent, R.layout.two_line_list_item);
        labelTextView = (TextView) itemView.findViewById(android.R.id.text1);
        descTextView = (TextView) itemView.findViewById(android.R.id.text2);
    }

    @Override
    public void bindTo(Cursor cursor) {
        StationCursor item = new StationCursor(cursor);
        labelTextView.setText(item.getName());
        descTextView.setText(item.getBitrate());
    }
}
