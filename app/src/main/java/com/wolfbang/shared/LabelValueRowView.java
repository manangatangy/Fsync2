package com.wolfbang.shared;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolfbang.fsync.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class LabelValueRowView extends FrameLayout {

    @IntDef({GRAVITY_LEFT, GRAVITY_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GravityMode {}

    public static final int GRAVITY_LEFT = 0;
    public static final int GRAVITY_RIGHT = 1;

    // Note: default values for size, color and gravity are in the layout.
    private static final float mDefaultLabelWeight = 0.5f;

    @BindView(R.id.row_label)
    TextView mLabel;
    @BindView(R.id.row_value)
    TextView mValue;

    public LabelValueRowView(Context context) {
        this(context, null);
    }
    public LabelValueRowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public LabelValueRowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }
    public LabelValueRowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        ((Activity)getContext())
                .getLayoutInflater()
                .inflate(R.layout.view_label_value_row, this, true);
        ButterKnife.bind(this);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(@Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LabelValueRowView, defStyleAttr, defStyleRes);

        setLabelWeight(a.getFloat(R.styleable.LabelValueRowView_labelWeight, mDefaultLabelWeight));

        setLabelSize(a.getDimensionPixelSize(R.styleable.LabelValueRowView_labelSize, 0));
        setValueSize(a.getDimensionPixelSize(R.styleable.LabelValueRowView_valueSize, 0));
        setLabelColor();
        setValueColor();
        setLabelText(a.getString(R.styleable.LabelValueRowView_labelText));
        setValueText(a.getString(R.styleable.LabelValueRowView_valueText));
        int labelGravity = a.getInt(R.styleable.LabelValueRowView_labelGravity, -1);
        if (labelGravity >= 0) {
            setLabelGravity(labelGravity);
        }
        int valueGravity = a.getInt(R.styleable.LabelValueRowView_valueGravity, -1);
        if (valueGravity >= 0) {
            setValueGravity(valueGravity);
        }
        a.recycle();
    }

    public void setLabelWeight(float labelWeight) {

    }
    public void setLabelSize(int labelSize) {
        if (labelSize > 0) {

        }
    }
    public void setValueSize(int valueSize) {
        if (valueSize > 0) {

        }
    }
    public void setLabelColor() {

    }
    public void setValueColor() {

    }
    public void setLabelText(String text) {
        mLabel.setText(text);
    }
    public void setValueText(String text) {
        mValue.setText(text);
    }
    public void setLabelGravity(@GravityMode int gravity) {
        mLabel.setGravity((gravity == GRAVITY_LEFT) ? Gravity.LEFT : Gravity.RIGHT);
    }
    public void setValueGravity(@GravityMode int gravity) {
        mValue.setGravity((gravity == GRAVITY_LEFT) ? Gravity.LEFT : Gravity.RIGHT);
    }

}
