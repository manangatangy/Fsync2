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
import com.wolfbang.fsync.ftpservice.model.compare.Action;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.ftpservice.model.mission.MissionNameData;
import com.wolfbang.shared.view.LabelValueColumnView;
import com.wolfbang.shared.view.LabelValueRowView;
import com.wolfbang.shared.view.LabelValueView;
import com.wolfbang.shared.view.NestedRadioGroup;
import com.wolfbang.shared.view.RadioLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author David Weiss
 * @date 16/4/18
 */
public abstract class TripleOptionTreeItemViewHolder
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

    @BindView(R.id.overwrite_a_radio)
    RadioLayout mRadioLayoutA;
    @BindView(R.id.overwrite_none_radio)
    RadioLayout mRadioLayoutNone;
    @BindView(R.id.overwrite_b_radio)
    RadioLayout mRadioLayoutB;

    @BindView(R.id.overwrite_a_item_value_view)
    LabelValueColumnView mItemViewOverwriteA;
    @BindView(R.id.overwrite_none_item_value_view)
    LabelValueColumnView mItemViewOverwriteNone;
    @BindView(R.id.overwrite_b_item_value_view)
    LabelValueColumnView mItemViewOverwriteB;

//    public static CommonTreeItemViewHolder makeViewHolder(@NonNull ViewGroup parent,
//                                                          MissionNameData missionNameData) {
//
//        return new CommonTreeItemViewHolder(view, missionNameData);
//    }

    public static View inflateLayout(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tree_item_triple, null);
        return view;
    }

    public TripleOptionTreeItemViewHolder(View itemView, MissionNameData missionNameData) {
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
        mItemViewOverwriteA.setLabel(mMissionNameData.getEndPointA().getEndPointName());
        mItemViewOverwriteB.setLabel(mMissionNameData.getEndPointB().getEndPointName());


//        mItemRowView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mTreeItemClickListener != null) {
//                    mTreeItemClickListener.onTreeItemClick(mFileNode);
//                }
//            }
//        });
    }

    private boolean mAllowPrecedenceCheckedNotification = true;

    public void setActionInSurface(Action action) {
        String actionLabel = "will be overwritten on";
        String actionValue = null;
        switch (action) {
        case OVERWRITE_ON_A:
            actionValue = mMissionNameData.getEndPointA().getEndPointName();
            break;
        case OVERWRITE_ON_B:
            actionValue = mMissionNameData.getEndPointB().getEndPointName();
            break;
        case DO_NOTHING:
            actionLabel = "will be";
            actionValue = "unaffected";
            break;
        }
        mSubHeading.setValue(null);
        mSubHeading.setLabel(actionLabel + ": " + actionValue);
    }

    public void setSelectedAction(Action action) {
        mAllowPrecedenceCheckedNotification = false;
        mRadioLayoutA.setChecked(false);
        mRadioLayoutB.setChecked(false);
        mRadioLayoutNone.setChecked(false);
        switch (action) {
        case OVERWRITE_ON_A:
            mRadioLayoutA.setChecked(true);
            break;
        case OVERWRITE_ON_B:
            mRadioLayoutB.setChecked(true);
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
        // The radio button selected is the one to keep
        // Therefore overwrite the other one.
        if (mAllowPrecedenceCheckedNotification) {
            Action action = null;
            switch (checkedId) {
            case R.id.overwrite_a_radio:
                action = Action.OVERWRITE_ON_B;
                break;
            case R.id.overwrite_none_radio:
                action = Action.DO_NOTHING;
                break;
            case R.id.overwrite_b_radio:
                action = Action.OVERWRITE_ON_A;
                break;
            }
            onActionSelected(action);
        }
    }
    //endregion

    public abstract void onActionSelected(Action action);

}

