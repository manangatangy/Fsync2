package com.wolfbang.shared.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.ArrayList;

public class NestedRadioGroup extends LinearLayout {

    // TODO remove this in favor of RadioGroup

    private ArrayList<Checkable> mCheckables = new ArrayList<>();
    private ArrayList<Integer> mNotifyIds = new ArrayList<>();
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
        findNestedCheckables(child.getId(), child);
    }

    private void findNestedCheckables(@IdRes int notifyId, final View child) {
        if (child instanceof Checkable) {
            mCheckables.add((Checkable)child);
            mNotifyIds.add(notifyId);
            child.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    int notifyId = -1;
                    for (int i = 0; i < mCheckables.size(); i++) {
                        Checkable checkable = mCheckables.get(i);
                        if (checkable == v) {
                            checkable.setChecked(true);
                            notifyId = mNotifyIds.get(i);
                        } else {
                            checkable.setChecked(false);
                        }
                    }
                    if (mOnCheckedChangeListener != null) {
                        mOnCheckedChangeListener.onCheckedChanged(null, notifyId);
                    }

                }
            });
        } else if (child instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) child;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                findNestedCheckables(notifyId, viewGroup.getChildAt(i));
            }
        }
    }

}
