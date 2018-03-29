package com.wolfbang.fsync.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.wolfbang.fsync.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 29 Mar 2018.
 */

public class PathElementView extends LinearLayout {

    @BindView(R.id.scroll_view_layout)
    LinearLayout mLinearLayout;

    public PathElementView(Context context) {
        super(context);
    }

    public PathElementView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PathElementView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public PathElementView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    {
        init();
    }
    private void init() {
        inflate(getContext(), R.layout.view_path_element, this);
        ButterKnife.bind(this);
    }

    protected void init(@NonNull Context context, @Nullable AttributeSet attrs) {
//        if (attrs == null) {
//            return;
//        }
//        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ItemRowView);
//
//        styledAttributes.recycle();
    }

}
