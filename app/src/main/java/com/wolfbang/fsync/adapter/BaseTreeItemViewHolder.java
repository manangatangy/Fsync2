package com.wolfbang.fsync.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.ftpservice.model.mission.MissionNameData;

/**
 * @author david
 * @date 7 Apr 2018.
 */

public abstract class BaseTreeItemViewHolder extends RecyclerView.ViewHolder {

    public static final int ITEM_TYPE_FILE = 0;
    public static final int ITEM_TYPE_DIR = 1;
    public static final int ITEM_TYPE_COMMON = 2;

    public BaseTreeItemViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(Node node, TreeItemRecyclerAdapter treeItemRecyclerAdapter);

    public static BaseTreeItemViewHolder makeViewHolder(@NonNull ViewGroup parent, int viewType,
                                                        MissionNameData missionNameData) {
        switch (viewType) {
        case ITEM_TYPE_FILE:
            return FileTreeItemViewHolder.makeViewHolder(parent);
        case ITEM_TYPE_DIR:
            return DirTreeItemViewHolder.makeViewHolder(parent);
        case ITEM_TYPE_COMMON:
            return CommonTreeItemViewHolder.makeViewHolder(parent, missionNameData);
        }
        return null;
    }

}
