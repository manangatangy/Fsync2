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

/**
 * @author David Weiss
 * @date 14/4/18
 */
public class RadioLayout extends LinearLayout implements Checkable {

    // Must match attrs.xml:RadioLayout.radioState
    public static final int RADIO_NONE = 0;
    public static final int RADIO_OFF = 1;
    public static final int RADIO_ON = 2;

    @IntDef({RADIO_NONE, RADIO_OFF, RADIO_ON})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RadioState {}

    private boolean mIsSwitchable;

    public RadioLayout(Context context) {
        super(context);
    }

    public RadioLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RadioLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public RadioLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.RadioLayout);
            mIsSwitchable = styledAttributes.getBoolean(R.styleable.RadioLayout_is_selectable, false);
            int radioState = styledAttributes.getInteger(R.styleable.RadioLayout_select_state, RADIO_NONE);
            setRadioState(radioState);
            styledAttributes.recycle();
        }
    }

    public void setRadioState(@RadioState int radioState) {
        this.getBackground().setLevel(radioState);
    }

    //region Checkable
    @Override
    public void setChecked(boolean checked) {
        if (mIsSwitchable) {
            setRadioState(checked ? RADIO_ON : RADIO_OFF);
        }
    }

    @Override
    public boolean isChecked() {
        return (this.getBackground().getLevel() == RADIO_ON);
    }

    /**
     * TODO this docco and impl. was copied from RadioButton.  Dunno why it behaves like this.
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
