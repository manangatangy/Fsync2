package com.wolfbang.fsync.view;
/**
 * (C) 2016. National Australia Bank [All rights reserved]. This product and related documentation
 * are protected by copyright restricting its use, copying, distribution, and decompilation. No part
 * of this product or related documentation may be reproduced in any form by any means without prior
 * written authorization of National Australia Bank. Unless otherwise arranged, third parties may
 * not have access to this product or related documents.
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import com.wolfbang.fsync.R;

import butterknife.ButterKnife;

/**
 * @author david
 * @date 28 Mar 2018.
 */

public class PathScrollView extends HorizontalScrollView {

    public PathScrollView(Context context) {
        super(context);
    }

    public PathScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public PathScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public PathScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    {
        init();
    }
    private void init() {
        inflate(getContext(), R.layout.view_row_item, this);
        ButterKnife.bind(this);
    }

    protected void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ItemRowView);

        setTitleText(styledAttributes.getString(R.styleable.ItemRowView_title));
        setDescriptionText(styledAttributes.getString(R.styleable.ItemRowView_description));
        setExtraDataText(styledAttributes.getString(R.styleable.ItemRowView_extra_data));

        @Visibility
        int chevronVisibility = styledAttributes.getInteger(R.styleable.ItemRowView_chevron_visible, -1);
        if (chevronVisibility >= 0) {
            setChevronVisibility(chevronVisibility);
        }

        @Visibility
        int dividerVisibility = styledAttributes.getInteger(R.styleable.ItemRowView_divider_visible, -1);
        if (dividerVisibility >= 0) {
            setDividerVisibility(dividerVisibility);
        }

        styledAttributes.recycle();
    }



}
