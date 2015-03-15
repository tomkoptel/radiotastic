/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.study.jam.radiotastic.SongItem;

import java.util.List;

public class SongsAdapter extends
        RecyclerView.Adapter<SongsAdapter.ItemViewHolder> {

    private List<SongItem> mData;

    public SongsAdapter() {
    }

    public void setDataset(List<SongItem> data) {
        mData = data;
        notifyDataSetChanged();
        // This isn't working
//        notifyItemRangeInserted(0, data.size());
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(android.R.layout.simple_list_item_2, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, int position) {
        SongItem item = mData.get(position);
        viewHolder.labelTextView.setText(item.getName());
        viewHolder.descTextView.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public SongItem getItem(int position) {
        return mData.get(position);
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