package com.wolfbang.shared.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build.VERSION_CODES;
import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolfbang.fsync.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public abstract class LabelValueView extends FrameLayout {

    public static final int GRAVITY_LEFT = 0;
    public static final int GRAVITY_RIGHT = 1;

    @IntDef({GRAVITY_LEFT, GRAVITY_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GravityMode {}


    // Note: default values for ??? are in the layout.
    @TwoColumnStyle
    private static final int mDefaultLabelStyle = TwoColumnStyle.NORMAL;
    @TwoColumnStyle
    private static final int mDefaultValueStyle = TwoColumnStyle.NORMAL;

    @IntDef({
            LabelValueView.TwoColumnStyle.NORMAL,
            LabelValueView.TwoColumnStyle.BOLD,
            LabelValueView.TwoColumnStyle.ITALIC,
            LabelValueView.TwoColumnStyle.BOLD_ITALIC
    })
    public @interface TwoColumnStyle {
        int NORMAL = Typeface.NORMAL;
        int BOLD = Typeface.BOLD;
        int ITALIC = Typeface.ITALIC;
        int BOLD_ITALIC = Typeface.BOLD_ITALIC;
        // Note: these values must match those of R2.styleable.LabelValueView_label/value_style
    }

    @BindView(R.id.row_label)
    TextView mLabelTextView;
    @BindView(R.id.row_value)
    TextView mValueTextView;

    public LabelValueView(Context context) {
        super(context);
    }

    public LabelValueView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LabelValueView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public LabelValueView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    {
        init();
    }
    protected void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.LabelValueView);

        setLabel(styledAttributes.getString(R.styleable.LabelValueView_label));
        setValue(styledAttributes.getString(R.styleable.LabelValueView_value));

        setLabelFont(styledAttributes.getString(R.styleable.LabelValueView_label_font));
        setLabelStyle(styledAttributes.getInteger(R.styleable.LabelValueView_label_style, mDefaultLabelStyle));

        @ColorRes int labelColorRes = styledAttributes.getResourceId(R.styleable.LabelValueView_label_color, -1);
        setLabelTextColor(labelColorRes);

        setLabelTextSize(TypedValue.COMPLEX_UNIT_PX,
                         styledAttributes.getDimension(R.styleable.LabelValueView_label_font_size, 0));

        setValueFont(styledAttributes.getString(R.styleable.LabelValueView_value_font));
        setValueStyle(styledAttributes.getInteger(R.styleable.LabelValueView_value_style, mDefaultValueStyle));

        @ColorRes int valueColorRes = styledAttributes.getResourceId(R.styleable.LabelValueView_value_color, -1);
        setValueTextColor(valueColorRes);

        setValueTextSize(TypedValue.COMPLEX_UNIT_PX,
                         styledAttributes.getDimension(R.styleable.LabelValueView_value_font_size, 0));

        int labelGravity = styledAttributes.getInteger(R.styleable.LabelValueView_label_gravity, -1);
        if (labelGravity >= 0) {
            setLabelGravity(labelGravity);
        }
        int valueGravity = styledAttributes.getInteger(R.styleable.LabelValueView_value_gravity, -1);
        if (valueGravity >= 0) {
            setValueGravity(valueGravity);
        }

        setLabelLayoutWeight(styledAttributes.getFloat(
                R.styleable.LabelValueView_label_layout_weight, 0));

        styledAttributes.recycle();
    }

    protected abstract void init();

    public void setLabel(int textId) {
        mLabelTextView.setText(textId);
    }

    public void setLabel(String text) {
        mLabelTextView.setText(text);
    }

    public void setValue(int textId) {
        mValueTextView.setText(textId);
    }

    public void setValue(String text) {
        mValueTextView.setText(text);
    }

    /**
     * @param labelLayoutWeight - weight of label as a fraction of 1.0
     * The value field's weight is also set to the remaining fraction.
     * e.g. labelLayoutWeight = 0.2 -> valueLayoutWeight = 0.8
     */
    public void setLabelLayoutWeight(float labelLayoutWeight) {
        if (0 < labelLayoutWeight && labelLayoutWeight < 1.0f) {
            float valueLayoutWeight = 1.0f - labelLayoutWeight;

            LinearLayout.LayoutParams labelParams = (LinearLayout.LayoutParams)mLabelTextView.getLayoutParams();
            labelParams.weight = labelLayoutWeight;
            mLabelTextView.setLayoutParams(labelParams);

            LinearLayout.LayoutParams valueParams = (LinearLayout.LayoutParams)mValueTextView.getLayoutParams();
            valueParams.weight = valueLayoutWeight;
            mValueTextView.setLayoutParams(valueParams);
        }
    }

    public void setLabelFont(@Nullable String labelFont) {
        if (labelFont != null) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), labelFont);
            setLabelTypeface(tf);
        }
    }

    public void setLabelTypeface(Typeface tf) {
        mLabelTextView.setTypeface(tf);
    }

    public void setLabelStyle(@TwoColumnStyle int style) {
        mLabelTextView.setTypeface(mLabelTextView.getTypeface(), style);
    }

    /**
     * Set the label text size to a given unit and value.
     * See {@link android.util.TypedValue} for the possible dimension units.
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     */
    public void setLabelTextSize(int unit, float size) {
        if (size != 0) {
            mLabelTextView.setTextSize(unit, size);
        }
    }

    public void setLabelTextColor(@ColorRes int color) {
        if (color != -1) {
            mLabelTextView.setTextColor(getContext().getResources().getColor(color));
        }
    }

    public void setValueFont(@Nullable String valueFont) {
        if (valueFont != null) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), valueFont);
            setValueTypeface(tf);
        }
    }

    public void setValueTypeface(Typeface tf) {
        mValueTextView.setTypeface(tf);
    }

    public void setValueStyle(@TwoColumnStyle int style) {
        mValueTextView.setTypeface(mValueTextView.getTypeface(), style);
    }

    public void setValueTextSize(int unit, float size) {
        if (size != 0) {
            mValueTextView.setTextSize(unit, size);
        }
    }

    public void setValueTextColor(@ColorRes int color) {
        if (color != -1) {
            mValueTextView.setTextColor(getContext().getResources().getColor(color));
        }
    }

    private int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void setLabelGravity(@GravityMode int gravity) {
        mLabelTextView.setGravity((gravity == GRAVITY_LEFT) ? Gravity.LEFT : Gravity.RIGHT);
    }
    public void setValueGravity(@GravityMode int gravity) {
        mValueTextView.setGravity((gravity == GRAVITY_LEFT) ? Gravity.LEFT : Gravity.RIGHT);
    }

}
