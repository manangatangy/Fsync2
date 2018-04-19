package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.wolfbang.fsync.model.compare.Action;
import com.wolfbang.fsync.model.compare.DirectoryOn;
import com.wolfbang.fsync.model.compare.NodeCounter;
import com.wolfbang.fsync.model.compare.TypeClashActionableDirNode;
import com.wolfbang.fsync.model.filetree.Node;
import com.wolfbang.fsync.model.mission.MissionNameData;

/**
 * @author david
 * @date 8 Apr 2018.
 */

public class ClashTreeItemViewHolder extends TripleOptionTreeItemViewHolder {

    private TypeClashActionableDirNode mTypeClashActionableDirNode;

    public static ClashTreeItemViewHolder makeViewHolder(@NonNull ViewGroup parent,
                                                         MissionNameData missionNameData) {
        return new ClashTreeItemViewHolder(TripleOptionTreeItemViewHolder.inflateLayout(parent),
                                           missionNameData);
    }

    public ClashTreeItemViewHolder(View itemView, MissionNameData missionNameData) {
        super(itemView, missionNameData);
    }

    @Override
    public void bind(Node node, TreeItemRecyclerAdapter treeItemRecyclerAdapter) {
        super.bind(node, treeItemRecyclerAdapter);

        mChevron.setVisibility(View.GONE);
        // Even though this node is a dir, we disallow clicking on the chevron, since
        // the children are not ActionableDir/FileNodes, and so would not appear due to
        // the filtering.
        mTypeClashActionableDirNode = (TypeClashActionableDirNode)node;
        NodeCounter counter = new NodeCounter(mTypeClashActionableDirNode);
        if (mTypeClashActionableDirNode.getDirectoryOn() == DirectoryOn.A) {
            // The counts for the dirNode are not filtered on action.
            mItemViewOverwriteA.setValue("Directory\n " + counter.getFilesDirsCountText());
            mItemViewOverwriteB.setValue(Node.formatDateWithBreak(mTypeClashActionableDirNode.getFileTimeStamp()));
        } else {
            mItemViewOverwriteA.setValue(Node.formatDateWithBreak(mTypeClashActionableDirNode.getFileTimeStamp()));
            mItemViewOverwriteB.setValue("Directory\n " + counter.getFilesDirsCountText());
        }

        setActionInSurface(mTypeClashActionableDirNode.getAction());
        setSelectedAction(mTypeClashActionableDirNode.getAction());

    }

    @Override
    public void onActionSelected(Action action) {
        setActionInSurface(action);
        // The updated action is held in the Node tree until a new compare is performed
        mTypeClashActionableDirNode.setAction(action);
    }

}
