package com.wolfbang.shared.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolfbang.fsync.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 31 Mar 2018.
 */

public class TextButtonChevron extends LinearLayout {

    @BindView(R.id.text_heading)
    TextView mTextHeading;
    @BindView(R.id.text_subheading)
    TextView mTextSubheading;

    @BindView(R.id.text_files)
    TextView mTextFiles;
    @BindView(R.id.text_dirs)
    TextView mTextDirs;

    @BindView(R.id.image_chevron)
    ImageView mImageChevron;

    public TextButtonChevron(Context context) {
        super(context);
    }

    public TextButtonChevron(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextButtonChevron(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public TextButtonChevron(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                             int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    {
        init();
    }
    private void init() {
        inflate(getContext(), R.layout.view_text_button_chevron, this);
        ButterKnife.bind(this);
    }

    protected void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TextButtonChevron);

        setHeadingText(styledAttributes.getString(R.styleable.TextButtonChevron_heading_text));
        setSubheadingText(styledAttributes.getString(R.styleable.TextButtonChevron_subheading_text));
        setFilesText(styledAttributes.getString(R.styleable.TextButtonChevron_files_text));
        setDirsText(styledAttributes.getString(R.styleable.TextButtonChevron_dirs_text));

        styledAttributes.recycle();
    }

    public void setHeadingText(String text) {
        mTextHeading.setText(text);
        mTextHeading.setVisibility((text == null || text.length() == 0) ? View.GONE : View.VISIBLE);
    }

    public void setSubheadingText(String text) {
        mTextSubheading.setText(text);
        mTextSubheading.setVisibility((text == null || text.length() == 0) ? View.GONE : View.VISIBLE);
    }

    public void setFilesText(String text) {
        mTextFiles.setText(text);
        mTextFiles.setVisibility((text == null || text.length() == 0) ? View.GONE : View.VISIBLE);
    }

    public void setDirsText(String text) {
        mTextDirs.setText(text);
        mTextDirs.setVisibility((text == null || text.length() == 0) ? View.GONE : View.VISIBLE);
    }

    public void setChevronOnClickListener(OnClickListener onClickListener) {
        mImageChevron.setOnClickListener(onClickListener);
    }

    public void setChevronVisible(boolean visible) {
        mImageChevron.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

}
