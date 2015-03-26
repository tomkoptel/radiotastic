/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.study.jam.radiotastic.SongDate;
import com.udacity.study.jam.radiotastic.SongItem;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SongsAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private View mHeaderView;
    private List<SongItem> mItems = new ArrayList<SongItem>();

    public SongsAdapter(View headerView) {
        mHeaderView = headerView;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        } else {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(android.R.layout.simple_list_item_2, viewGroup, false);

            return new ItemViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            ItemViewHolder holder = (ItemViewHolder) viewHolder;
            SongItem item = getItem(position - 1);
            SongDate date = item.getDate();
            String title = item.getTitle();
            if (date != null) {
                Date utcTime = new Date((long) (date.getSec() * TimeUnit.SECONDS.toMillis(1)));
                long now = System.currentTimeMillis();
                CharSequence result = DateUtils.getRelativeTimeSpanString(utcTime.getTime(),
                        now, DateUtils.SECOND_IN_MILLIS);
                title = title + "\n" + result;
            }
            holder.labelTextView.setText(item.getName());
            holder.descTextView.setText(title);
        }
    }

    public void setDataset(List<SongItem> data) {
        mItems = data;
        notifyDataSetChanged();
    }

    public SongItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return mItems.size();
        } else {
            return mItems.size() + 1;
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
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