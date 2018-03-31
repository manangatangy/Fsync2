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
import android.widget.RadioButton;
import android.widget.TextView;

import com.wolfbang.fsync.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 31 Mar 2018.
 */

public class NestedRadioButton extends LinearLayout {

    @BindView(R.id.radio_button)
    RadioButton mRadioButton;
    @BindView(R.id.text_heading)
    TextView mTextHeading;
    @BindView(R.id.text_subheading)
    TextView mTextSubheading;
    @BindView(R.id.image_chevron)
    ImageView mImageChevron;

    public NestedRadioButton(Context context) {
        super(context);
    }

    public NestedRadioButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NestedRadioButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public NestedRadioButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    {
        init();
    }
    private void init() {
        inflate(getContext(), R.layout.view_nested_radio_button, this);
        ButterKnife.bind(this);
    }

    protected void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.NestedRadioButton);

        setButtonText(styledAttributes.getString(R.styleable.NestedRadioButton_button_text));
        setHeadingText(styledAttributes.getString(R.styleable.NestedRadioButton_heading_text));
        setSubheadingText(styledAttributes.getString(R.styleable.NestedRadioButton_subheading_text));

        styledAttributes.recycle();
    }

    public void setButtonText(String text) {
        mRadioButton.setText(text);
    }

    public void setHeadingText(String text) {
        mTextHeading.setText(text);
        mTextHeading.setVisibility((text == null || text.length() == 0) ? View.GONE : View.VISIBLE);
    }

    public void setSubheadingText(String text) {
        mTextSubheading.setText(text);
        mTextSubheading.setVisibility((text == null || text.length() == 0) ? View.GONE : View.VISIBLE);
    }

    public void setChecked(boolean checked) {
        mRadioButton.setChecked(checked);
    }

    public void setChevronOnClickListener(OnClickListener onClickListener) {
        mImageChevron.setVisibility(View.VISIBLE);
        mImageChevron.setOnClickListener(onClickListener);
    }
}
