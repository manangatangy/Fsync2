package com.wolfbang.fsync.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wolfbang.fsync.R;
import com.wolfbang.fsync.model.filetree.Node;
import com.wolfbang.fsync.model.mission.MissionNameData;
import com.wolfbang.shared.view.LabelValueRowView;

import butterknife.BindView;

/**
 * @author david
 * @date 7 Apr 2018.
 */

public abstract class BaseTreeItemViewHolder extends RecyclerView.ViewHolder {

    public static final int ITEM_TYPE_FILE = 0;
    public static final int ITEM_TYPE_DIR = 1;
    public static final int ITEM_TYPE_COMMON = 2;
    public static final int ITEM_TYPE_DIR_ACTIONABLE = 3;
    public static final int ITEM_TYPE_UNIQUE = 4;
    public static final int ITEM_TYPE_CLASH = 5;

    // These components are in layout_tree_item_base
    @BindView(R.id.item_heading)
    TextView mHeading;
    @BindView(R.id.item_sub_heading)
    LabelValueRowView mSubHeading;
    @BindView(R.id.item_chevron)
    ImageView mChevron;

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
        case ITEM_TYPE_CLASH:
            return ClashTreeItemViewHolder.makeViewHolder(parent, missionNameData);
        case ITEM_TYPE_UNIQUE:
            return UniqueTreeItemViewHolder.makeViewHolder(parent, missionNameData);
        }
        return null;
    }

}
