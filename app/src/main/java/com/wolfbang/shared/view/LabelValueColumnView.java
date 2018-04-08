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
 * @date 8 Apr 2018.
 */

public class LabelValueColumnView extends LabelValueView  {

    @Override
    protected void init() {
        inflate(getContext(), R.layout.view_label_value_column, this);
        ButterKnife.bind(this);
    }

    public LabelValueColumnView(Context context) {
        super(context);
    }

    public LabelValueColumnView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LabelValueColumnView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public LabelValueColumnView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

}
