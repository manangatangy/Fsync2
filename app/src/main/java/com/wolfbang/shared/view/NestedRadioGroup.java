package com.wolfbang.shared.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.ArrayList;

public class NestedRadioGroup extends LinearLayout {

    private ArrayList<View> mCheckables = new ArrayList<View>();
    private OnCheckedChangeListener mOnCheckedChangeListener;

    public NestedRadioGroup(Context context) {
        super(context);
    }

    public NestedRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public NestedRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NestedRadioGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        findNestedCheckables(child);
    }

    private void findNestedCheckables(final View child) {
        if (child instanceof Checkable) {
            mCheckables.add(child);
            child.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < mCheckables.size(); i++) {
                        Checkable view = (Checkable)mCheckables.get(i);
                        if (view == v) {
                            view.setChecked(true);
                        } else {
                            view.setChecked(false);
                        }
                    }
                    if (mOnCheckedChangeListener != null) {
                        mOnCheckedChangeListener.onCheckedChanged(null, v.getId());
                    }

                }
            });
        } else if (child instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) child;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                findNestedCheckables(viewGroup.getChildAt(i));
            }
        }
    }

}
