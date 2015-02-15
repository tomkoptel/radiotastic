/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.study.jam.radiotastic.R;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends
        RecyclerView.Adapter<CategoryAdapter.ItemViewHolder> {

    private List<String> mData = new ArrayList<>();

    public CategoryAdapter() {
        mData.add("Category 1");
        mData.add("Category 2");
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_station_list, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, int position) {
        String item = mData.get(position);
        viewHolder.labelTextView.setText(item);
        viewHolder.descTextView.setText("");
    }

    @Override
    public int getItemCount() {
        return mData.size();
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
