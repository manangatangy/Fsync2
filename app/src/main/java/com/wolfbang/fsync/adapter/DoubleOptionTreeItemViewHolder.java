package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.wolfbang.fsync.R;
import com.wolfbang.fsync.model.compare.Action;
import com.wolfbang.fsync.model.filetree.Node;
import com.wolfbang.fsync.model.mission.MissionNameData;
import com.wolfbang.shared.view.LabelValueColumnView;
import com.wolfbang.shared.view.LabelValueRowView;
import com.wolfbang.shared.view.LabelValueView;
import com.wolfbang.shared.view.NestedRadioGroup;
import com.wolfbang.shared.view.RadioLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author David Weiss
 * @date 19/4/18
 */
public abstract class DoubleOptionTreeItemViewHolder
        extends BaseTreeItemViewHolder implements OnCheckedChangeListener {

    protected MissionNameData mMissionNameData;

    @BindView(R.id.swipe_layout_item)
    SwipeLayout mSwipeLayout;

    // These components are in layout_tree_item_base
    @BindView(R.id.item_heading)
    TextView mHeading;
    @BindView(R.id.item_sub_heading)
    LabelValueRowView mSubHeading;
    @BindView(R.id.item_chevron)
    ImageView mChevron;

    // These components are in layout_tree_item_triple_radio
    @BindView(R.id.layout_bottom)
    LinearLayout mLayoutBottom;
    @BindView(R.id.precedence_radio_group)
    NestedRadioGroup mRadioGroup;

    @BindView(R.id.copy_ab_radio)
    RadioLayout mRadioLayoutAB;
    @BindView(R.id.copy_none_radio)
    RadioLayout mRadioLayoutNone;

    @BindView(R.id.copy_ab_item_value_view)
    LabelValueColumnView mItemViewCopyAB;
    @BindView(R.id.copy_none_item_value_view)
    LabelValueColumnView mItemViewCopyNone;

    public static View inflateLayout(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tree_item_double, null);
        return view;
    }

    /**
     * A DoubleOptionTreeItemViewHolder represents a node that is a file in one
     * endPoint and not present in the other endpoint.  The user can select on the
     * bottom layout, which action to use; copy from one particular endpoint, or do nothing.
     */
    public DoubleOptionTreeItemViewHolder(View itemView, MissionNameData missionNameData) {
        super(itemView);
        mMissionNameData = missionNameData;
        ButterKnife.bind(this, itemView);
        mSwipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        mRadioGroup.setOnCheckedChangeListener(this);

        mSubHeading.setLabelGravity(LabelValueView.GRAVITY_RIGHT);
        mSubHeading.setLabelLayoutWeight(0.999f);

//        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
//            @Override
//            public void onOpen(SwipeLayout layout) {
//                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
//            }
//        });

    }

    @Override
    public void bind(Node node, TreeItemRecyclerAdapter treeItemRecyclerAdapter) {
        mHeading.setText(node.getName());        // TODO max 13 chars - insert newline as needed
    }

    private boolean mAllowPrecedenceCheckedNotification = true;

    public void setActionInSurface(Action action) {
        String actionLabel = "will be copied to";
        String actionValue = null;
        switch (action) {
        case COPY_TO_A:
            actionValue = mMissionNameData.getEndPointA().getEndPointName();
            break;
        case COPY_TO_B:
            actionValue = mMissionNameData.getEndPointB().getEndPointName();
            break;
        case DO_NOTHING:
            actionLabel = "will be";
            actionValue = "not copied";
            break;
        }
        mSubHeading.setValue(null);
        mSubHeading.setLabel(actionLabel + ": " + actionValue);
    }

    public void setSelectedAction(Action action) {
        mAllowPrecedenceCheckedNotification = false;
        mRadioLayoutAB.setChecked(false);
        mRadioLayoutNone.setChecked(false);
        switch (action) {
        case COPY_TO_A:
        case COPY_TO_B:
            mRadioLayoutAB.setChecked(true);
            break;
        case DO_NOTHING:
            mRadioLayoutNone.setChecked(true);
            break;
        }
        mAllowPrecedenceCheckedNotification = true;
    }

    //region RadioGroup.OnCheckedChangeListener
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // The radio button selected is the one to be copied from
        // Therefore copy to the other one.
        if (mAllowPrecedenceCheckedNotification) {
            Action action = null;
            switch (checkedId) {
            case R.id.copy_ab_radio:
                action = getTargetCopyTo();
                break;
            case R.id.copy_none_radio:
                action = Action.DO_NOTHING;
                break;
            }
            onActionSelected(action);
        }
    }
    //endregion

    public abstract void onActionSelected(Action action);

    // Returns the COPY_A or COPY_B, depending on which endPoint is the unique
    public abstract Action getTargetCopyTo();

}
