package com.wolfbang.fsync.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION_CODES;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolfbang.fsync.R;
import com.wolfbang.shared.view.LabelValueRowView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 19 Mar 2018.
 */

public class ItemRowView extends LinearLayout {

    public static final int VISIBILITY_NO = 0;
    public static final int VISIBILITY_YES = 1;

    @IntDef({VISIBILITY_NO, VISIBILITY_YES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {}

    @BindView(R.id.item_title)
    TextView mTitleTextView;
    @BindView(R.id.item_image_chevron)
    ImageView mChevronImage;
    @BindView(R.id.item_row_view)
    LabelValueRowView mItemRowView;
    @BindView(R.id.item_divider)
    View mDividerView;

    public ItemRowView(Context context) {
        super(context);
    }

    public ItemRowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ItemRowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public ItemRowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

    public void setTitleText(String title) {
        mTitleTextView.setText(title);
    }

    public void setDescriptionText(String description) {
        mItemRowView.setLabel(description);
    }

    public void setExtraDataText(String extraData) {
        mItemRowView.setValue(extraData);
    }

    public void setChevronVisibility(@Visibility int visibility) {
        mChevronImage.setVisibility(visibility == VISIBILITY_YES ? View.VISIBLE : View.GONE);
    }

    public void setDividerVisibility(@Visibility int visibility) {
        mDividerView.setVisibility(visibility == VISIBILITY_YES ? View.VISIBLE : View.GONE);
    }

}
