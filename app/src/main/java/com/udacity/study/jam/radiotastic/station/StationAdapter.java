/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.station;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.pojo.StationItem;

import java.util.Collections;
import java.util.List;

public class StationAdapter extends
        RecyclerView.Adapter<StationAdapter.ListItemViewHolder> {

    private List<StationItem> mData = Collections.emptyList();

    public StationAdapter() {
    }

    public void setDataset(List<StationItem> data) {
        mData = data;
        // This isn't working
        notifyItemRangeInserted(0, data.size());
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_station_list, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        StationItem item = mData.get(position);
        Context context = viewHolder.labelTextView.getContext();
        viewHolder.labelTextView.setText(item.getName());
        viewHolder.descTextView.setText(context.getString(R.string.format_station_desc,
                item.getCountry(), item.getBitrate()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public StationItem getItem(int position) {
        return mData.get(position);
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView labelTextView;
        private final TextView descTextView;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            labelTextView = (TextView) itemView.findViewById(android.R.id.text1);
            descTextView = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }
}
