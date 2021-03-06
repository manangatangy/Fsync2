package com.wolfbang.shared.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.wolfbang.fsync.R;

import butterknife.ButterKnife;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class LabelValueRowView extends LabelValueView  {

    @Override
    protected void init() {
        inflate(getContext(), R.layout.view_label_value_row, this);
        ButterKnife.bind(this);
    }

    public LabelValueRowView(Context context) {
        super(context);
    }

    public LabelValueRowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LabelValueRowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public LabelValueRowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

}
