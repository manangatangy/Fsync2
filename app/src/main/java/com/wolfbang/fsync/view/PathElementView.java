package com.wolfbang.fsync.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolfbang.fsync.R;

import org.apache.commons.text.WordUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 29 Mar 2018.
 */

public class PathElementView extends LinearLayout {

    public static final int TEXT_LINE_LENGTH = 24;

    @BindView(R.id.path_element_layout)
    LinearLayout mLinearLayout;
    @BindView(R.id.path_element_image)
    ImageView mImageView;
    @BindView(R.id.path_element_text)
    TextView mTextView;

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

    public void setText(String text) {
        String wrapped = WordUtils.wrap(text, TEXT_LINE_LENGTH, "\n", true);
        mTextView.setText(wrapped);
    }


}
