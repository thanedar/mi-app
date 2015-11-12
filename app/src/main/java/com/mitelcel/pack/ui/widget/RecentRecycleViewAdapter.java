package com.mitelcel.pack.ui.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.resp.BeanGetRecentActivityResponse.UserActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by sudhanshu.thanedar on 12/11/2015
 */
public class RecentRecycleViewAdapter extends EmptyRecyclerView.Adapter<RecentRecycleViewAdapter.VHHistory>{

    List<UserActivity> userActivities;

    @Inject
    MiApiClient miApiClient;

    public RecentRecycleViewAdapter(List<UserActivity> userActivities) {
        this.userActivities = userActivities;
    }

    public RecentRecycleViewAdapter() {
        this.userActivities = new ArrayList<>();
    }

    @Override
    public VHHistory onCreateViewHolder(ViewGroup parent, int viewType) {
        RecentItemLayout recentItemLayout = (RecentItemLayout)View.inflate(parent.getContext(), R.layout.item_recent, null);
        return  new VHHistory(recentItemLayout);
    }

    @Override
    public void onBindViewHolder(VHHistory holder, int position) {
        holder.bind(userActivities.get(position));
    }

    @Override
    public int getItemCount() {
        return userActivities.size();
    }

    public static final class VHHistory extends RecyclerView.ViewHolder{

        RecentItemLayout itemView;

        public VHHistory(RecentItemLayout itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void bind(UserActivity userActivity){
            itemView.bind(userActivity);
        }
    }

    public void addData(List<UserActivity> newListHistory){
        int start = userActivities.size();
        userActivities.addAll(newListHistory);
        notifyItemRangeInserted(start, userActivities.size());
    }

    public void replaceData(List<UserActivity> userActivities){
        this.userActivities.clear();
        this.userActivities.addAll(userActivities);
        notifyDataSetChanged();
    }

    public void clearData(){
        userActivities.clear();
        notifyDataSetChanged();
    }
}
