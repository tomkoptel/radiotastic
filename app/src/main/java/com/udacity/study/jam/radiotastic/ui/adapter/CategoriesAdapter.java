/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.db.category.CategoryCursor;


public class CategoriesAdapter extends CursorRecyclerAdapter<CategoriesAdapter.ItemViewHolder> {

    public CategoriesAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, Cursor cursor) {
        CategoryCursor item = new CategoryCursor(cursor);
        viewHolder.labelTextView.setText(item.getName());
        viewHolder.descTextView.setText(item.getDescription());
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_station_list, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    public final static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView labelTextView;
        private final TextView descTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            labelTextView = (TextView) itemView.findViewById(android.R.id.text1);
            descTextView = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }

}
