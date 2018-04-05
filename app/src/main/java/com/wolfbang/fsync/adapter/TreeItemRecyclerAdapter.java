package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.wolfbang.fsync.R;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.view.ItemRowView;

/**
 * @author david
 * @date 20 Mar 2018.
 */

public class TreeItemRecyclerAdapter
        extends RecyclerView.Adapter<TreeItemRecyclerAdapter.TreeItemViewHolder> {

    private Node[] mChildren;
    private FileNode mSelected;

    private TreeItemClickListener mTreeItemClickListener;

    public void setTreeItemClickListener(TreeItemClickListener listener) {
        mTreeItemClickListener = listener;
    }

    public interface TreeItemClickListener {
        void onTreeItemClick(FileNode fileNode);
    }

    @NonNull
    @Override
    public TreeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tree_item, null);
        return new TreeItemViewHolder(view);
    }

    public void setNodeItems(Node[] nodes) {
        mChildren = nodes;
    }

    @Override
    public void onBindViewHolder(@NonNull TreeItemViewHolder holder, int position) {
        mSelected = (FileNode)mChildren[position];
        holder.bind(mSelected);
    }

    @Override
    public int getItemCount() {
        return (mChildren == null) ? 0 : mChildren.length;
    }

    public class TreeItemViewHolder extends RecyclerView.ViewHolder {

        private ItemRowView mItemRowView;
        private FileNode mFileNode;

        public TreeItemViewHolder(View itemView) {
            super(itemView);
            mItemRowView = (ItemRowView)itemView;
        }

        public void bind(FileNode fileNode) {
            mFileNode = fileNode;
            // TODO set fields in mItemRowView from fields in mFileNode
            mItemRowView.setChevronVisibility(ItemRowView.VISIBILITY_NO);
            mItemRowView.setTitleText(mFileNode.getName());
            mItemRowView.setDescriptionText(mFileNode.getTimeStampAsText());
            String extraData = "";
            if (mFileNode instanceof DirNode) {
                DirNode dirNode = (DirNode)fileNode;
                extraData = "[ " + dirNode.size() + " children]";
            }
            mItemRowView.setExtraDataText(extraData);

            mItemRowView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTreeItemClickListener != null) {
                        mTreeItemClickListener.onTreeItemClick(mFileNode);
                    }
                }
            });
        }

    }

}
