package com.wolfbang.shared.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION_CODES;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolfbang.fsync.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 31 Mar 2018.
 */

public class NestedRadioButton extends LinearLayout implements Checkable {

    // Must match attrs.xml:NestedRadioButton.radioState
    public static final int RADIO_NONE = 0;
    public static final int RADIO_OFF = 1;
    public static final int RADIO_ON = 2;

    @IntDef({RADIO_NONE, RADIO_OFF, RADIO_ON})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RadioState {}

    @BindView(R.id.layout_main)
    LinearLayout mLayoutMain;

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

        if (styledAttributes.getBoolean(R.styleable.NestedRadioButton_is_radio_button, false)) {
            int radioState = styledAttributes.getInteger(R.styleable.NestedRadioButton_radio_state, RADIO_OFF);
            setRadioState(radioState);
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggle();
                }
            });
        }


        setHeadingText(styledAttributes.getString(R.styleable.NestedRadioButton_heading_text));
        setSubheadingText(styledAttributes.getString(R.styleable.NestedRadioButton_subheading_text));
        setFilesText(styledAttributes.getString(R.styleable.NestedRadioButton_files_text));
        setDirsText(styledAttributes.getString(R.styleable.NestedRadioButton_dirs_text));

        styledAttributes.recycle();
    }

    public void setRadioState(@RadioState int radioState) {
        mLayoutMain.getBackground().setLevel(radioState);
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
    //region Checkable
    @Override
    public void setChecked(boolean checked) {
        setRadioState(checked ? RADIO_ON : RADIO_OFF);
    }

    @Override
    public boolean isChecked() {
        return (mLayoutMain.getBackground().getLevel() == RADIO_ON);
    }

    /**
     * TODO this docco and impl. was copired from RadioButton.  Dunno why it behaves like this.
     * If the radio button is already checked, this method will not toggle the radio button.
     */
    @Override
    public void toggle() {
        if (!isChecked()) {
            setChecked(true);
        }
    }
    //endregion

}
