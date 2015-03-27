/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.adapter.holder;

import android.content.res.Resources;
import android.database.Cursor;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.db.station.StationCursor;
import com.udacity.study.jam.radiotastic.ui.adapter.easy.EasyViewHolder;

public class StationViewHolder extends EasyViewHolder<Cursor> {
    private final TextView labelTextView;
    private final ImageView iconView;
    private final ColorGenerator mGenerator;
    private final int px;

    public StationViewHolder(ViewGroup parent) {
        super(parent, R.layout.station_line_list_item);
        Resources resources = parent.getContext().getResources();
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                40, resources.getDisplayMetrics());

        labelTextView = (TextView) itemView.findViewById(android.R.id.text1);
        iconView = (ImageView) itemView.findViewById(android.R.id.icon);

        mGenerator = ColorGenerator.DEFAULT;
    }

    @Override
    public void bindTo(Cursor cursor) {
        StationCursor item = new StationCursor(cursor);
        labelTextView.setText(item.getName());
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRoundRect(item.getBitrate(), mGenerator.getColor(item.getBitrate()), px);
        iconView.setImageDrawable(textDrawable);
    }
}
