package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolfbang.fsync.R;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.view.ItemRowView;

/**
 * @author david
 * @date 7 Apr 2018.
 */

public class FileTreeItemViewHolder extends BaseTreeItemViewHolder {

    private FileNode mFileNode;

    public static FileTreeItemViewHolder makeViewHolder(@NonNull ViewGroup parent) {
        // TODO use correct layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tree_item, null);
        return new FileTreeItemViewHolder(view);
    }

    public FileTreeItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Node node, TreeItemRecyclerAdapter treeItemRecyclerAdapter) {
        mFileNode = (FileNode)node;
        mItemRowView.setChevronVisibility(ItemRowView.VISIBILITY_NO);
        mItemRowView.setTitleText(mFileNode.getName());
        mItemRowView.setDescriptionText(mFileNode.getTimeStampAsText());
        mItemRowView.setExtraDataText("");

//        mItemRowView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mTreeItemClickListener != null) {
//                    mTreeItemClickListener.onTreeItemClick(mFileNode);
//                }
//            }
//        });
    }

}
