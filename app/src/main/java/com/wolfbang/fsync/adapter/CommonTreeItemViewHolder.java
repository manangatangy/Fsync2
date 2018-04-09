package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.wolfbang.fsync.R;
import com.wolfbang.fsync.ftpservice.model.compare.Action;
import com.wolfbang.fsync.ftpservice.model.compare.CommonActionableFileNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.ftpservice.model.mission.MissionNameData;
import com.wolfbang.shared.view.LabelValueColumnView;
import com.wolfbang.shared.view.NestedRadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 8 Apr 2018.
 */

public class CommonTreeItemViewHolder extends BaseTreeItemViewHolder implements OnCheckedChangeListener {

    private CommonActionableFileNode mCommonActionableFileNode;
    private MissionNameData mMissionNameData;

    @BindView(R.id.swipe_layout_item)
    SwipeLayout mSwipeLayout;

    @BindView(R.id.layout_surface)
    LinearLayout mLayoutSurface;
    @BindView(R.id.item_title)
    TextView mTitle;
    @BindView(R.id.item_action)
    LabelValueColumnView mAction;

    @BindView(R.id.layout_bottom)
    LinearLayout mLayoutBottom;
    @BindView(R.id.option_radio_group)
    NestedRadioGroup mRadioGroup;
    @BindView(R.id.overwrite_a_item_value_view)
    LabelValueColumnView mItemViewOverwriteA;
    @BindView(R.id.overwrite_none_item_value_view)
    LabelValueColumnView mItemViewOverwriteNone;
    @BindView(R.id.overwrite_b_item_value_view)
    LabelValueColumnView mItemViewOverwriteB;

    public static CommonTreeItemViewHolder makeViewHolder(@NonNull ViewGroup parent,
                                                          MissionNameData missionNameData) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tree_item_common_file, null);
        return new CommonTreeItemViewHolder(view, missionNameData);
    }

    public CommonTreeItemViewHolder(View itemView, MissionNameData missionNameData) {
        super(itemView);
        mMissionNameData = missionNameData;
        ButterKnife.bind(this, itemView);
        mSwipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        mRadioGroup.setOnCheckedChangeListener(this);

//        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
//            @Override
//            public void onOpen(SwipeLayout layout) {
//                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
//            }
//        });

    }

    @Override
    public void bind(Node node, TreeItemRecyclerAdapter treeItemRecyclerAdapter) {
        mCommonActionableFileNode = (CommonActionableFileNode)node;

        mTitle.setText(mCommonActionableFileNode.getName());        // TODO max 13 chars

        mItemViewOverwriteA.setLabel(mMissionNameData.getEndPointA().getEndPointName());
        mItemViewOverwriteB.setLabel(mMissionNameData.getEndPointB().getEndPointName());
        // TODO proper date formatting  "30 Mar 2018\n00:00:00 +1100"
        String txt = Node.formatDate(mCommonActionableFileNode.getTimeStampA());
        txt = txt.replace(" ", "\n");
        mItemViewOverwriteA.setValue("30 Mar 2018\n00:00:00 +1100");
        txt = Node.formatDate(mCommonActionableFileNode.getTimeStampB());
        txt = txt.replace(" ", "\n");
        mItemViewOverwriteB.setValue("30 Mar 2018\n00:00:00 +1100");

        setActionInSurface(mCommonActionableFileNode.getAction());
        setSelectedAction(mCommonActionableFileNode.getAction());

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
        String actionLabel = "will overwrite on";
        String actionValue = null;
        switch (action) {
        case OVERWRITE_ON_A:
            actionValue = mMissionNameData.getEndPointA().getEndPointName();
            break;
        case OVERWRITE_ON_B:
            actionValue = mMissionNameData.getEndPointB().getEndPointName();
            break;
        case DO_NOTHING:
            actionLabel = "will do";
            actionValue = "nothing";
            break;
        }
        mAction.setLabel(actionLabel);
        mAction.setValue(actionValue);
    }

    public void setSelectedAction(Action action) {
        mAllowPrecedenceCheckedNotification = false;
        mItemViewOverwriteA.setChecked(false);
        mItemViewOverwriteB.setChecked(false);
        mItemViewOverwriteNone.setChecked(false);
        switch (action) {
        case OVERWRITE_ON_A:
            mItemViewOverwriteA.setChecked(true);
            break;
        case OVERWRITE_ON_B:
            mItemViewOverwriteB.setChecked(true);
            break;
        case DO_NOTHING:
            mItemViewOverwriteNone.setChecked(true);
            break;
        }
        mAllowPrecedenceCheckedNotification = true;
    }

    //region RadioGroup.OnCheckedChangeListener
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mAllowPrecedenceCheckedNotification) {
            Action action = null;
            switch (checkedId) {
            case R.id.overwrite_a_item_value_view:
                action = Action.OVERWRITE_ON_A;
                break;
            case R.id.overwrite_none_item_value_view:
                action = Action.DO_NOTHING;
                break;
            case R.id.overwrite_b_item_value_view:
                action = Action.OVERWRITE_ON_B;
                break;
            }
            setActionInSurface(action);

            // TODO what to do with this event ?
            // The updated action is held in the Node tree until a new compare is performed
            mCommonActionableFileNode.setAction(action);
//            if (precedence != Precedence.NONE) {
//                getPresenter().onPrecedenceChecked(precedence);
//            }
        }
    }
    //endregion


}
