package com.wolfbang.shared.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wolfbang.fsync.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 22 Mar 2018.
 */

public class TextButtonView extends FrameLayout {

    @TextButtonView.TwoColumnStyle
    private static final int mDefaultStyle = LabelValueRowView.TwoColumnStyle.NORMAL;

    @IntDef({
            TextButtonView.TwoColumnStyle.NORMAL,
            TextButtonView.TwoColumnStyle.BOLD,
            TextButtonView.TwoColumnStyle.ITALIC,
            TextButtonView.TwoColumnStyle.BOLD_ITALIC
    })
    public @interface TwoColumnStyle {
        int NORMAL = Typeface.NORMAL;
        int BOLD = Typeface.BOLD;
        int ITALIC = Typeface.ITALIC;
        int BOLD_ITALIC = Typeface.BOLD_ITALIC;
        // Note: these values must match those of R2.styleable.LabelValueRowView_label/value_style
    }

    @BindView(R.id.text_button_text_view)
    TextView mTextView;
    @BindView(R.id.text_button_button_view)
    Button mButton;

    public TextButtonView(Context context) {
        super(context);
    }
    public TextButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public TextButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public TextButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TextButtonView);

        setLabel(styledAttributes.getString(R.styleable.TextButtonView_text));
        setButton(styledAttributes.getString(R.styleable.TextButtonView_button));

        setLabelFont(styledAttributes.getString(R.styleable.TextButtonView_text_font));
        setLabelStyle(styledAttributes.getInteger(R.styleable.TextButtonView_text_style, mDefaultStyle));

        @ColorRes int labelColorRes = styledAttributes.getResourceId(R.styleable.TextButtonView_text_color, -1);
        setLabelTextColor(labelColorRes);

        setLabelTextSize(TypedValue.COMPLEX_UNIT_PX,
                styledAttributes.getDimension(R.styleable.TextButtonView_text_font_size, 0));

        styledAttributes.recycle();
    }

    private void init() {
        inflate(getContext(), R.layout.view_text_button, this);
        ButterKnife.bind(this);
    }

    public void setLabel(int textId) {
        mTextView.setText(textId);
    }

    public void setLabel(String text) {
        mTextView.setText(text);
    }

    public void setButton(int textId) {
        mButton.setText(textId);
    }

    public void setButton(String text) {
        mButton.setText(text);
    }

    public void setButtonEnabled(boolean enabled) {
        mButton.setEnabled(enabled);
    }

    public void setLabelFont(@Nullable String labelFont) {
        if (labelFont != null) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), labelFont);
            setLabelTypeface(tf);
        }
    }

    public void setLabelTypeface(Typeface tf) {
        mTextView.setTypeface(tf);
    }

    public void setLabelStyle(@LabelValueRowView.TwoColumnStyle int style) {
        mTextView.setTypeface(mTextView.getTypeface(), style);
    }

    /**
     * Set the label text size to a given unit and value.
     * See {@link android.util.TypedValue} for the possible dimension units.
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     */
    public void setLabelTextSize(int unit, float size) {
        if (size != 0) {
            mTextView.setTextSize(unit, size);
        }
    }

    public void setLabelTextColor(@ColorRes int color) {
        if (color != -1) {
            mTextView.setTextColor(getContext().getResources().getColor(color));
        }
    }

}
