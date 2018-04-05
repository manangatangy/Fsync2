package com.wolfbang.fsync.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.wolfbang.fsync.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 28 Mar 2018.
 */

public class PathScrollerView extends HorizontalScrollView {

    @BindView(R.id.path_scroll_layout)
    LinearLayout mLinearLayout;

    private OnPathElementClickListener mOnPathElementClickListener;

    public PathScrollerView(Context context) {
        super(context);
    }

    public PathScrollerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PathScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public PathScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    {
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_path_scroller, this);
        ButterKnife.bind(this);
    }

    public void setOnPathElementClickListener(OnPathElementClickListener onPathElementClickListener) {
        mOnPathElementClickListener = onPathElementClickListener;
    }

    public int size() {
        return mLinearLayout.getChildCount();
    }

    public void clear() {
        mLinearLayout.removeAllViews();
    }

    /**
     * Append the view to the current children list and attach onClick listener.
     */
    public void push(final PathElementView pathElementView) {
        final int index = size();
        mLinearLayout.addView(pathElementView);
        pathElementView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPathElementClickListener != null) {
                    mOnPathElementClickListener.onPathElementClick(index, pathElementView);
                }
            }
        });
    }

    /**
     * @return the element at the end (top) of the list, or null if no children.
     */
    public PathElementView top() {
        return (size() == 0) ? null : (PathElementView)mLinearLayout.getChildAt(size() - 1);
    }

    /**
     * Drop a single child from the end of the list.
     * Return the new size of the list (after the child was removed).
     */
    public int pop() {
        if (size() > 0) {
            mLinearLayout.removeViewAt(size() - 1);
        }
        return size();
    }

    /**
     * Drop children from the end of the list, until the element specified by the newTopIndex,
     * is the new top.  If this parameter is -1 then the entire child list is emptied.
     * @return the new top, which is either the view specified by the newTopIndex, or
     * null if the list is emptied.
     */
    public PathElementView pop(int newTopIndex) {
        while (true) {
            int size = size();
            if (size == 0) {
                return null;    // List has been emptied.
            }
            if (size == (newTopIndex + 1)) {
                return top();   // New top has been reached.
            }
            mLinearLayout.removeViewAt(size - 1);
        }
    }

    protected void init(@NonNull Context context, @Nullable AttributeSet attrs) {
//        if (attrs == null) {
//            return;
//        }
//        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ItemRowView);
//
//        styledAttributes.recycle();
    }


    public interface OnPathElementClickListener {
        /**
         * @param index - the index of the selected path element, in the parent scroller
         * view's child list (this value will be; 0 <= index < size)
         * @param pathElementView - the element that was selected by click.
         */
        void onPathElementClick(int index, PathElementView pathElementView);
    }

}
