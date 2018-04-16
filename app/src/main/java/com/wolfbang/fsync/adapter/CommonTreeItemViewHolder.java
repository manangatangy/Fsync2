package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.wolfbang.fsync.model.compare.Action;
import com.wolfbang.fsync.model.compare.CommonActionableFileNode;
import com.wolfbang.fsync.model.filetree.Node;
import com.wolfbang.fsync.model.mission.MissionNameData;

/**
 * @author david
 * @date 8 Apr 2018.
 */

public class CommonTreeItemViewHolder extends TripleOptionTreeItemViewHolder {

    private CommonActionableFileNode mCommonActionableFileNode;

    public static CommonTreeItemViewHolder makeViewHolder(@NonNull ViewGroup parent,
                                                          MissionNameData missionNameData) {
        return new CommonTreeItemViewHolder(TripleOptionTreeItemViewHolder.inflateLayout(parent),
                                            missionNameData);
    }

    public CommonTreeItemViewHolder(View itemView, MissionNameData missionNameData) {
        super(itemView, missionNameData);
    }

    @Override
    public void bind(Node node, TreeItemRecyclerAdapter treeItemRecyclerAdapter) {
        super.bind(node, treeItemRecyclerAdapter);

        mChevron.setVisibility(View.GONE);
        mCommonActionableFileNode = (CommonActionableFileNode)node;
        mItemViewOverwriteA.setValue(Node.formatDateWithBreak(mCommonActionableFileNode.getTimeStampA()));
        mItemViewOverwriteB.setValue(Node.formatDateWithBreak(mCommonActionableFileNode.getTimeStampB()));

        setActionInSurface(mCommonActionableFileNode.getAction());
        setSelectedAction(mCommonActionableFileNode.getAction());
    }

    @Override
    public void onActionSelected(Action action) {
        setActionInSurface(action);
        // The updated action is held in the Node tree until a new compare is performed
        mCommonActionableFileNode.setAction(action);
    }

}
