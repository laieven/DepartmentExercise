package com.lbj.frameworkexercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder>{
    private final List<AppInfo> mAppInfoList;
    private final Context mContext;


    public AppInfoAdapter(List<AppInfo> appInfoList, Context context) {
        mContext = context;
        mAppInfoList = appInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppInfo appInfo = mAppInfoList.get(position);
        holder.label.setText(appInfo.label);
        holder.icon.setImageDrawable(appInfo.icon);
        holder.isSystem.setText(appInfo.isSystem ? "yes" : "no");
    }

    @Override
    public int getItemCount() {
        return mAppInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        TextView isSystem;
        ImageView icon;
        public ViewHolder(View view) {
            super(view);
            label = view.findViewById(R.id.label);
            isSystem = view.findViewById(R.id.is_system);
            icon = view.findViewById(R.id.icon);
        }

    }



}
