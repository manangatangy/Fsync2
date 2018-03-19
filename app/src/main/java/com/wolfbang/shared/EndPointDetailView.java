package com.wolfbang.shared;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolfbang.fsync.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 18 Mar 2018.
 */

public class EndPointDetailView extends LinearLayout {

    @BindView(R.id.heading_row_view)
    LabelValueRowView mHeadingRowView;
    @BindView(R.id.host_row_view)
    LabelValueRowView mHostNameRowView;
    @BindView(R.id.user_row_view)
    LabelValueRowView mUserNameRowView;
    @BindView(R.id.password_row_view)
    LabelValueRowView mPasswordRowView;
    @BindView(R.id.root_dir_row_view)
    LabelValueRowView mRootDirRowView;

    public EndPointDetailView(Context context) {
        super(context);
    }

    public EndPointDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public EndPointDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public EndPointDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    {
        init();
    }
    private void init() {
        inflate(getContext(), R.layout.view_end_point_detail, this);
        ButterKnife.bind(this);
    }

    public LabelValueRowView getHeadingRowView() {
        return mHeadingRowView;
    }

    public LabelValueRowView getHostNameRowView() {
        return mHostNameRowView;
    }

    public LabelValueRowView getUserNameRowView() {
        return mUserNameRowView;
    }

    public LabelValueRowView getPasswordRowView() {
        return mPasswordRowView;
    }

    public LabelValueRowView getRootDirRowView() {
        return mRootDirRowView;
    }

}
