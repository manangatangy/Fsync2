package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolfbang.fsync.R;

/**
 * @author David Weiss
 * @date 15/4/18
 */
public class ActionableDirTreeItemViewHolder extends DirTreeItemViewHolder {

    public static ActionableDirTreeItemViewHolder makeViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tree_item_base, null);
        return new ActionableDirTreeItemViewHolder(view);
    }

    public ActionableDirTreeItemViewHolder(View itemView) {
        super(itemView);
    }

}
