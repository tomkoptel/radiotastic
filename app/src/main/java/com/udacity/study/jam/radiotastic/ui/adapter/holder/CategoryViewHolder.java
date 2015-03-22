/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.adapter.holder;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.db.category.CategoryCursor;
import com.udacity.study.jam.radiotastic.ui.adapter.easy.EasyViewHolder;

public class CategoryViewHolder extends EasyViewHolder<Cursor> {
    private final TextView labelTextView;
    private final TextView descTextView;

    public CategoryViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.three_line_list_item);
        labelTextView = (TextView) itemView.findViewById(android.R.id.text1);
        descTextView = (TextView) itemView.findViewById(android.R.id.text2);
    }

    @Override
    public void bindTo(Cursor cursor) {
        CategoryCursor item = new CategoryCursor(cursor);
        labelTextView.setText(item.getName());
        if (TextUtils.isEmpty(item.getDescription())) {
            descTextView.setText(R.string.not_available);
        } else {
            descTextView.setText(item.getDescription());
        }
    }
}
