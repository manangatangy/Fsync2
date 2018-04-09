package com.wolfbang.shared.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION_CODES;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.wolfbang.fsync.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 8 Apr 2018.
 */

public class LabelValueColumnView extends LabelValueView implements Checkable {

    public static final int SELECT_NONE = 0;
    public static final int SELECT_OFF = 1;
    public static final int SELECT_ON = 2;

    @IntDef({SELECT_NONE, SELECT_OFF, SELECT_ON})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SelectState {}

    private boolean mIsSelectable;

    @BindView(R.id.linear_layout)
    LinearLayout mLinearLayout;

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

    @Override
    protected void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        super.init(context, attrs);

        if (attrs != null) {
            TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.LabelValueColumnView);
            mIsSelectable = styledAttributes.getBoolean(R.styleable.LabelValueColumnView_is_selectable, false);
            int radioState = styledAttributes.getInteger(R.styleable.LabelValueColumnView_select_state, SELECT_NONE);
            setRadioState(radioState);
            styledAttributes.recycle();
        }
    }

    public void setRadioState(@SelectState int radioState) {
        mLinearLayout.getBackground().setLevel(radioState);
    }

    //region Checkable
    @Override
    public void setChecked(boolean checked) {
        if (mIsSelectable) {
            setRadioState(checked ? SELECT_ON : SELECT_OFF);
        }
    }

    @Override
    public boolean isChecked() {
        return (mLinearLayout.getBackground().getLevel() == SELECT_ON);
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
