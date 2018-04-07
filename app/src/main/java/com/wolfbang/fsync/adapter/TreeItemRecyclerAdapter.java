package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wolfbang.fsync.adapter.DirTreeItemViewHolder.DirTreeItemClickListener;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;

/**
 * @author david
 * @date 20 Mar 2018.
 */

public class TreeItemRecyclerAdapter
        extends RecyclerView.Adapter<BaseTreeItemViewHolder>
        implements DirTreeItemClickListener {

    private DirTreeItemClickListener mDirTreeItemClickListener;
    private Node[] mChildren;
    private FileNode mSelected;

    public void setDirTreeItemClickListener(DirTreeItemClickListener dirTreeItemClickListener) {
        mDirTreeItemClickListener = dirTreeItemClickListener;
    }

    public void setNodeItems(Node[] nodes) {
        mChildren = nodes;
    }

    //region RecyclerView.Adapter<TreeItemRecyclerAdapter.TreeItemViewHolder>
    @Override
    public int getItemViewType(int position) {
        if (mChildren[position] instanceof DirNode) {
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
        return BaseTreeItemViewHolder.makeViewHolder(parent, viewType);
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

//    public class TreeItemViewHolder extends RecyclerView.ViewHolder {
//
//        private ItemRowView mItemRowView;
//        private FileNode mFileNode;
//
//        public TreeItemViewHolder(View itemView) {
//            super(itemView);
//            mItemRowView = (ItemRowView)itemView;
//        }
//
//        public void bind(FileNode fileNode) {
//            mFileNode = fileNode;
//            // TODO set fields in mItemRowView from fields in mFileNode
//            mItemRowView.setChevronVisibility(ItemRowView.VISIBILITY_NO);
//            mItemRowView.setTitleText(mFileNode.getName());
//            mItemRowView.setDescriptionText(mFileNode.getTimeStampAsText());
//            String extraData = "";
//            if (mFileNode instanceof DirNode) {
//                DirNode dirNode = (DirNode)fileNode;
//                extraData = "[ " + dirNode.size() + " children]";
//            }
//            mItemRowView.setExtraDataText(extraData);
//
//            mItemRowView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mTreeItemClickListener != null) {
//                        mTreeItemClickListener.onTreeItemClick(mFileNode);
//                    }
//                }
//            });
//        }
//
//    }

}
