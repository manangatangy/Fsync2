package com.wolfbang.shared;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @author david
 * @date 20 Mar 2018.
 */

public class DefaultLayoutManager extends LinearLayoutManager {

    public DefaultLayoutManager(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                             ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
