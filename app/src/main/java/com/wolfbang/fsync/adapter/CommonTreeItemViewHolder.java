package com.wolfbang.fsync.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.wolfbang.fsync.R;
import com.wolfbang.fsync.ftpservice.model.compare.CommonActionableFileNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.ftpservice.model.mission.MissionNameData;
import com.wolfbang.shared.view.LabelValueColumnView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 8 Apr 2018.
 */

public class CommonTreeItemViewHolder extends BaseTreeItemViewHolder {

    private CommonActionableFileNode mCommonActionableFileNode;
    private MissionNameData mMissionNameData;

    @BindView(R.id.swipe_layout_item)
    SwipeLayout mSwipeLayout;

    @BindView(R.id.layout_surface)
    LinearLayout mLayoutSurface;
    @BindView(R.id.item_title)
    TextView mTitle;
    @BindView(R.id.item_desc)
    TextView mDesc;

    @BindView(R.id.layout_bottom)
    LinearLayout mLayoutBottom;
    @BindView(R.id.overwrite_a_item_value_view)
    LabelValueColumnView mItemViewOverwriteA;
    @BindView(R.id.overwrite_b_item_value_view)
    LabelValueColumnView mItemViewOverwriteB;
    @BindView(R.id.overwrite_radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.overwrite_radio_button_a)
    RadioButton mRadioButtonA;
    @BindView(R.id.overwrite_radio_button_none)
    RadioButton mRadioButtonNone;
    @BindView(R.id.overwrite_radio_button_b)
    RadioButton mRadioButtonB;

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
    }

    @Override
    public void bind(Node node, TreeItemRecyclerAdapter treeItemRecyclerAdapter) {
        mCommonActionableFileNode = (CommonActionableFileNode)node;

        mTitle.setText(mCommonActionableFileNode.getName());
        // TODO max 13 chars
        mItemViewOverwriteA.setLabel(mMissionNameData.getEndPointA().getEndPointName());
        mItemViewOverwriteB.setLabel(mMissionNameData.getEndPointB().getEndPointName());
        // TODO proper date formatting
        String txt = Node.formatDate(mCommonActionableFileNode.getTimeStampA());
        txt = txt.replace(" ", "\n");
        mItemViewOverwriteA.setValue(txt);
        txt = Node.formatDate(mCommonActionableFileNode.getTimeStampB());
        txt = txt.replace(" ", "\n");
        mItemViewOverwriteB.setValue(txt);

//        mItemRowView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mTreeItemClickListener != null) {
//                    mTreeItemClickListener.onTreeItemClick(mFileNode);
//                }
//            }
//        });
    }

}
