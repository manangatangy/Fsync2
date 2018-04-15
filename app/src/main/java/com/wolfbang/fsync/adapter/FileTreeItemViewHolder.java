package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolfbang.fsync.R;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.shared.view.LabelValueView;

import butterknife.ButterKnife;

/**
 * @author david
 * @date 7 Apr 2018.
 */

public class FileTreeItemViewHolder extends BaseTreeItemViewHolder {

    private FileNode mFileNode;

    public static FileTreeItemViewHolder makeViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tree_item, null);
        return new FileTreeItemViewHolder(view);
    }

    public FileTreeItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mSubHeading.setLabelGravity(LabelValueView.GRAVITY_RIGHT);
        mSubHeading.setLabelLayoutWeight(0.999f);
    }

    @Override
    public void bind(Node node, TreeItemRecyclerAdapter treeItemRecyclerAdapter) {
        mFileNode = (FileNode)node;
        mHeading.setText(mFileNode.getName());
        mChevron.setVisibility(View.GONE);
        mSubHeading.setValue(null);
        mSubHeading.setLabel(mFileNode.getTimeStampAsText());
    }

}
