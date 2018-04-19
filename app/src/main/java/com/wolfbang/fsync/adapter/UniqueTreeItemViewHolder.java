package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.wolfbang.fsync.model.compare.Action;
import com.wolfbang.fsync.model.compare.UniqueActionableFileNode;
import com.wolfbang.fsync.model.compare.UniqueTo;
import com.wolfbang.fsync.model.filetree.Node;
import com.wolfbang.fsync.model.mission.MissionNameData;

/**
 * @author david
 * @date 8 Apr 2018.
 */

public class UniqueTreeItemViewHolder extends DoubleOptionTreeItemViewHolder {

    private UniqueActionableFileNode mUniqueActionableFileNode;

    public static UniqueTreeItemViewHolder makeViewHolder(@NonNull ViewGroup parent,
                                                         MissionNameData missionNameData) {
        return new UniqueTreeItemViewHolder(DoubleOptionTreeItemViewHolder.inflateLayout(parent),
                                            missionNameData);
    }

    public UniqueTreeItemViewHolder(View itemView, MissionNameData missionNameData) {
        super(itemView, missionNameData);
    }

    @Override
    public void bind(Node node, TreeItemRecyclerAdapter treeItemRecyclerAdapter) {
        super.bind(node, treeItemRecyclerAdapter);
        mUniqueActionableFileNode = (UniqueActionableFileNode)node;

        mChevron.setVisibility(View.GONE);
        mItemViewCopyAB.setLabel(mUniqueActionableFileNode.getUniqueTo() == UniqueTo.A
                                 ? mMissionNameData.getEndPointA().getEndPointName()
                                 : mMissionNameData.getEndPointB().getEndPointName());
        mItemViewCopyAB.setValue(mUniqueActionableFileNode.getTimeStampAsText());

        setActionInSurface(mUniqueActionableFileNode.getAction());
        setSelectedAction(mUniqueActionableFileNode.getAction());
    }

    @Override
    public void onActionSelected(Action action) {
        setActionInSurface(action);
        // The updated action is held in the Node tree until a new compare is performed
        mUniqueActionableFileNode.setAction(action);
    }

    @Override
    public Action getTargetCopyTo() {
        return (mUniqueActionableFileNode.getUniqueTo() == UniqueTo.A)
               ? Action.COPY_TO_B : Action.COPY_TO_A;
    }
}
