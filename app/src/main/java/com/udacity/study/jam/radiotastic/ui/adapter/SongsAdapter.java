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

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.SongDate;
import com.udacity.study.jam.radiotastic.SongItem;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SongsAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM_HEADER = 1;
    private static final int VIEW_TYPE_ITEM = 2;
    private final boolean mTwoPane;

    private View mHeaderView;
    private List<SongItem> mItems = new ArrayList<SongItem>();

    public SongsAdapter(View headerView, boolean twoPane) {
        mHeaderView = headerView;
        mTwoPane = twoPane;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        }
        if (position == 1 && mTwoPane) {
            return VIEW_TYPE_ITEM_HEADER;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        } else if (viewType == VIEW_TYPE_ITEM_HEADER) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.song_line_list_item_header, viewGroup, false);
            return new ItemHeaderViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.song_line_list_item, viewGroup, false);
            return new ItemViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            ItemViewHolder holder = (ItemViewHolder) viewHolder;
            SongItem item = getItem(position - 1);
            SongDate date = item.getDate();
            if (date != null) {
                Date utcTime = new Date((long) (date.getSec() * TimeUnit.SECONDS.toMillis(1)));
                long now = System.currentTimeMillis();
                CharSequence result = DateUtils.getRelativeTimeSpanString(utcTime.getTime(),
                        now, DateUtils.SECOND_IN_MILLIS);
                holder.dateTextView.setText(result);
            } else {
                holder.dateTextView.setVisibility(View.GONE);
            }
            holder.labelTextView.setText(item.getName());
            holder.descTextView.setText(item.getTitle());
        }
        if (viewHolder instanceof ItemHeaderViewHolder) {
            ItemHeaderViewHolder holder = (ItemHeaderViewHolder) viewHolder;
            holder.labelTextView.setText(R.string.author);
            holder.descTextView.setText(R.string.track);
            holder.dateTextView.setText(R.string.played_at);
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

    static class ItemHeaderViewHolder extends ItemViewHolder {
        public ItemHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        public final TextView labelTextView;
        public final TextView descTextView;
        public final TextView dateTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            labelTextView = (TextView) itemView.findViewById(android.R.id.title);
            descTextView = (TextView) itemView.findViewById(android.R.id.summary);
            dateTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

}