package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.wolfbang.fsync.R;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;

import butterknife.ButterKnife;

/**
 * @author david
 * @date 7 Apr 2018.
 */

public class DirTreeItemViewHolder extends BaseTreeItemViewHolder implements OnClickListener {

    public interface DirTreeItemClickListener {
        void onDirTreeItemClick(DirNode dirNode);
    }

    private DirTreeItemClickListener mDirTreeItemClickListener;
    protected DirNode mDirNode;

    public static DirTreeItemViewHolder makeViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tree_item_base, null);
        return new DirTreeItemViewHolder(view);
    }

    public DirTreeItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bind(Node node, TreeItemRecyclerAdapter treeItemRecyclerAdapter) {
        mDirNode = (DirNode) node;
        mDirTreeItemClickListener = treeItemRecyclerAdapter;
        mHeading.setText(mDirNode.getName());
        mChevron.setVisibility(View.VISIBLE);
        mSubHeading.setValue(null);

        // Note that mDirNode may be ActionableDirNode in which case the getAction will be non-null
        mSubHeading.setLabel("holds " + mDirNode.getFilesDirsCountText(treeItemRecyclerAdapter.getAction()));
    }

    @Override
    public void onClick(View v) {
        mDirTreeItemClickListener.onDirTreeItemClick(mDirNode);
    }
}
