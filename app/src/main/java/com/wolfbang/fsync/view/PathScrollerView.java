package com.wolfbang.fsync.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.wolfbang.fsync.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 28 Mar 2018.
 */

public class PathScrollerView extends HorizontalScrollView {

    @BindView(R.id.path_scroll_layout)
    LinearLayout mLinearLayout;

    public PathScrollerView(Context context) {
        super(context);
    }

    public PathScrollerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PathScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public PathScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    {
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_path_scroller, this);
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


    interface PathElementSelectHandler {
        void onSelectPathElement();
    }

}
