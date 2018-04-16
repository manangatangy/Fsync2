package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.wolfbang.fsync.adapter.DirTreeItemViewHolder.DirTreeItemClickListener;
import com.wolfbang.fsync.model.compare.Action;
import com.wolfbang.fsync.model.compare.DirectoryOn;
import com.wolfbang.fsync.model.compare.TypeClashActionableDirNode;
import com.wolfbang.fsync.model.filetree.Node;
import com.wolfbang.fsync.model.mission.MissionNameData;

/**
 * @author david
 * @date 8 Apr 2018.
 */

public class ClashTreeItemViewHolder extends TripleOptionTreeItemViewHolder implements OnClickListener {

    private DirTreeItemClickListener mDirTreeItemClickListener;
    private TypeClashActionableDirNode mTypeClashActionableDirNode;

    public static ClashTreeItemViewHolder makeViewHolder(@NonNull ViewGroup parent,
                                                         MissionNameData missionNameData) {
        return new ClashTreeItemViewHolder(TripleOptionTreeItemViewHolder.inflateLayout(parent),
                                           missionNameData);
    }

    public ClashTreeItemViewHolder(View itemView, MissionNameData missionNameData) {
        super(itemView, missionNameData);
        itemView.setOnClickListener(this);
    }

    @Override
    public void bind(Node node, TreeItemRecyclerAdapter treeItemRecyclerAdapter) {
        super.bind(node, treeItemRecyclerAdapter);

        mChevron.setVisibility(View.VISIBLE);

        mTypeClashActionableDirNode = (TypeClashActionableDirNode)node;
        if (mTypeClashActionableDirNode.getDirectoryOn() == DirectoryOn.A) {
            // The counts for the dirNode are not filtered on action.
            mItemViewOverwriteA.setValue("Directory\n " + mTypeClashActionableDirNode.getFilesDirsCountText());
            mItemViewOverwriteB.setValue(Node.formatDateWithBreak(mTypeClashActionableDirNode.getFileTimeStamp()));
        } else {
            mItemViewOverwriteA.setValue(Node.formatDateWithBreak(mTypeClashActionableDirNode.getFileTimeStamp()));
            mItemViewOverwriteB.setValue("Directory\n " + mTypeClashActionableDirNode.getFilesDirsCountText());
        }

        setActionInSurface(mTypeClashActionableDirNode.getAction());
        setSelectedAction(mTypeClashActionableDirNode.getAction());

        mDirTreeItemClickListener = treeItemRecyclerAdapter;
    }

    @Override
    public void onActionSelected(Action action) {
        setActionInSurface(action);
        // The updated action is held in the Node tree until a new compare is performed
        mTypeClashActionableDirNode.setAction(action);
    }

    @Override
    public void onClick(View v) {
        mDirTreeItemClickListener.onDirTreeItemClick(mTypeClashActionableDirNode);
    }

}
