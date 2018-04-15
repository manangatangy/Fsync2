package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wolfbang.fsync.adapter.DirTreeItemViewHolder.DirTreeItemClickListener;
import com.wolfbang.fsync.ftpservice.model.compare.Action;
import com.wolfbang.fsync.ftpservice.model.compare.CommonActionableFileNode;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.ftpservice.model.mission.MissionNameData;

/**
 * @author david
 * @date 20 Mar 2018.
 */

public class TreeItemRecyclerAdapter
        extends RecyclerView.Adapter<BaseTreeItemViewHolder>
        implements DirTreeItemClickListener {

    private MissionNameData mMissionNameData;
    private DirTreeItemClickListener mDirTreeItemClickListener;
    private Node[] mChildren;
    private FileNode mSelected;
    @Nullable
    private Action mAction;         // Action being filtered on.

    public void setDirTreeItemClickListener(DirTreeItemClickListener dirTreeItemClickListener) {
        mDirTreeItemClickListener = dirTreeItemClickListener;
    }

    public void setMissionNameData(MissionNameData missionNameData) {
        mMissionNameData = missionNameData;
    }

    public void setAction(@Nullable Action action) {
        mAction = action;
    }

    public void setNodeItems(Node[] nodes) {
        mChildren = nodes;
    }

    public Action getAction() {
        return mAction;
    }

    //region RecyclerView.Adapter<TreeItemRecyclerAdapter.TreeItemViewHolder>
    @Override
    public int getItemViewType(int position) {
        if (mChildren[position] instanceof CommonActionableFileNode) {
            return BaseTreeItemViewHolder.ITEM_TYPE_COMMON;
        } else if (mChildren[position] instanceof DirNode) {
            return BaseTreeItemViewHolder.ITEM_TYPE_DIR;
        } else if (mChildren[position] instanceof FileNode) {
            return BaseTreeItemViewHolder.ITEM_TYPE_FILE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @NonNull
    @Override
    public BaseTreeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BaseTreeItemViewHolder.makeViewHolder(parent, viewType, mMissionNameData);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseTreeItemViewHolder holder, int position) {
        mSelected = (FileNode)mChildren[position];
        holder.bind(mSelected, this);
    }

    @Override
    public int getItemCount() {
        return (mChildren == null) ? 0 : mChildren.length;
    }
    //endregion

    @Override
    public void onDirTreeItemClick(DirNode dirNode) {
        if (mDirTreeItemClickListener != null) {
            mDirTreeItemClickListener.onDirTreeItemClick(dirNode);
        }
    }

}
