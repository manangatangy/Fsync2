package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.wolfbang.fsync.R;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.view.ItemRowView;

/**
 * @author david
 * @date 7 Apr 2018.
 */

public class DirTreeItemViewHolder extends BaseTreeItemViewHolder implements OnClickListener {

    public interface DirTreeItemClickListener {
        void onDirTreeItemClick(DirNode dirNode);
    }

    private DirTreeItemClickListener mDirTreeItemClickListener;
    private DirNode mDirNode;

    public static DirTreeItemViewHolder makeViewHolder(@NonNull ViewGroup parent) {
        // TODO use correct layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tree_item, null);
        return new DirTreeItemViewHolder(view);
    }

    public DirTreeItemViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(Node node, TreeItemRecyclerAdapter treeItemRecyclerAdapter) {
        mDirNode = (DirNode) node;
        mDirTreeItemClickListener = treeItemRecyclerAdapter;
        mItemRowView.setChevronVisibility(ItemRowView.VISIBILITY_YES);
        mItemRowView.setTitleText(mDirNode.getName());
        String extraData = "[ " + mDirNode.size() + " children]";
        mItemRowView.setExtraDataText(extraData);
        mItemRowView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mDirTreeItemClickListener.onDirTreeItemClick(mDirNode);
    }
}
