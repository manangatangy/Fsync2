package com.wolfbang.fsync.missionconfirm.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lsmvp.simplemvp.AbstractMvpViewFragment;
import com.lsmvp.simplemvp.ModelUpdater;
import com.lsmvp.simplemvp.ObjectRegistry;
import com.wolfbang.fsync.R;
import com.wolfbang.fsync.application.FsyncApplication;
import com.wolfbang.fsync.ftpservice.model.compare.Action;
import com.wolfbang.fsync.ftpservice.model.compare.Precedence;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.mission.MissionNameData;
import com.wolfbang.fsync.ftpservice.model.mission.ScanResult;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Model;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Navigation;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Presenter;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.View;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.ViewFieldID;
import com.wolfbang.fsync.missionconfirm._di.DaggerMissionConfirmComponent;
import com.wolfbang.fsync.missionconfirm._di.MissionConfirmComponent;
import com.wolfbang.fsync.missionconfirm._di.MissionConfirmModule;
import com.wolfbang.fsync.treebrowse.impl.TreeBrowseFragment;
import com.wolfbang.shared.BackClickHandler;
import com.wolfbang.shared.view.AnimatingActivity;
import com.wolfbang.shared.view.LabelValueRowView;
import com.wolfbang.shared.view.NestedRadioButton;
import com.wolfbang.shared.view.NestedRadioGroup;
import com.wolfbang.shared.view.SingleFragActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author david
 * @date 22 Mar 2018.
 */

public class MissionConfirmFragment
        extends AbstractMvpViewFragment<Presenter, Model, MissionConfirmComponent>
        implements View, Navigation, OnCheckedChangeListener, BackClickHandler {

    private static final String MCF_MISSION_NAME_DATA = "MCF_MISSION_NAME_DATA";
    private static final String MCF_SCAN_RESULT = "MCF_SCAN_RESULT";

    @BindView(R.id.heading_row_view)
    LabelValueRowView mHeadingRowView;

    @BindView(R.id.precedence_radio_group)
    NestedRadioGroup mPrecedenceRadioGroup;
    @BindView(R.id.radio_from_a)
    NestedRadioButton mRadioFromA;
    @BindView(R.id.radio_bidirectional)
    NestedRadioButton mRadioBoth;
    @BindView(R.id.radio_from_b)
    NestedRadioButton mRadioFromB;


    @BindView(R.id.comparison_cardview)
    CardView mComparisonCardView;
    @BindView(R.id.comparison_radio_group)
    NestedRadioGroup mComparisonRadioGroup;
    @BindView(R.id.radio_to_a)
    NestedRadioButton mRadioToA;
    @BindView(R.id.radio_to_b)
    NestedRadioButton mRadioToB;
    @BindView(R.id.radio_on_a)
    NestedRadioButton mRadioOnA;
    @BindView(R.id.radio_on_b)
    NestedRadioButton mRadioOnB;
    @BindView(R.id.radio_clash)
    NestedRadioButton mRadioClash;

    @BindView(R.id.sync_button)
    Button mSyncButton;

    public static Intent createIntent(Context context, MissionNameData missionNameData, ScanResult scanResult) {
        Intent intent = new SingleFragActivity.Builder(context, MissionConfirmFragment.class.getName())
                .setDisplayHomeAsUpEnabled(true)
                .setTitle("Confirm")
                .build();

        ObjectRegistry objectRegistry = FsyncApplication.getFsyncApplicationComponent().getObjectRegistry();
        intent.putExtra(MCF_MISSION_NAME_DATA, objectRegistry.put(missionNameData));
        intent.putExtra(MCF_SCAN_RESULT, objectRegistry.put(scanResult));

        return intent;
    }

    //region SimpleMVP
    @NonNull
    @Override
    protected MissionConfirmComponent createComponent() {
        return DaggerMissionConfirmComponent.builder()
                .fsyncApplicationComponent(FsyncApplication.getFsyncApplicationComponent())
                .missionConfirmModule(new MissionConfirmModule())
                .build();
    }

    @Override
    protected void doInjection(@NonNull MissionConfirmComponent component) {
        component.inject(this);
    }

    @NonNull
    @Override
    protected Presenter createPresenter(@NonNull MissionConfirmComponent component) {
        return component.providePresenter();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mission_confirm;
    }

    @Override
    protected void onBound() {
        super.onBound();
        mPrecedenceRadioGroup.setOnCheckedChangeListener(this);
    }

    @Nullable
    @Override
    protected ModelUpdater<Model> getModelInitializer() {
        return new ModelUpdater<Model>() {
            @Override
            public void updateModel(Model model) {
                Bundle args = getArguments();

                String key1 = args.getString(MCF_MISSION_NAME_DATA, "");
                MissionNameData missionNameData = getObjectRegistry().get(key1);
                model.setMissionNameData(missionNameData);

                String key2 = args.getString(MCF_SCAN_RESULT, "");
                ScanResult scanResult = getObjectRegistry().get(key2);
                model.setScanResult(scanResult);
            }
        };
    }
    //endregion

    private boolean mInhibitPrecedenceCheckedNotification = false;

    //region RadioGroup.OnCheckedChangeListener
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (!mInhibitPrecedenceCheckedNotification) {
            Precedence precedence;
            switch (checkedId) {
            case R.id.radio_from_a:
                precedence = Precedence.A;
                break;
            case R.id.radio_bidirectional:
                precedence = Precedence.NEWEST;
                break;
            case R.id.radio_from_b:
                precedence = Precedence.B;
                break;
            default:
                precedence = Precedence.NONE;
                break;
            }
            if (precedence != Precedence.NONE) {
                getPresenter().onPrecedenceChecked(precedence);
            }
        }
    }
    //endregion

    //region Contract.View
    @Override
    public void setPrecedence(Precedence precedence) {
        mInhibitPrecedenceCheckedNotification = true;
        switch (precedence) {
        case A:
            mRadioFromA.setChecked(true);
            break;
        case NEWEST:
            mRadioBoth.setChecked(true);
            break;
        case B:
            mRadioFromB.setChecked(true);
            break;
        }
        mInhibitPrecedenceCheckedNotification = false;
    }

    @Override
    public void setMissionName(String missionName) {
        mHeadingRowView.setValue(missionName);
        mRadioClash.setSubheadingText("Clashes need resolving");
    }

    @Override
    public void setEndPointNameA(String endPointNameA) {
        mRadioFromA.setSubheadingText(endPointNameA);
        mRadioToA.setSubheadingText(endPointNameA);
        mRadioOnA.setSubheadingText(endPointNameA);
    }

    @Override
    public void setEndPointNameB(String endPointNameB) {
        mRadioFromB.setSubheadingText(endPointNameB);
        mRadioToB.setSubheadingText(endPointNameB);
        mRadioOnB.setSubheadingText(endPointNameB);
    }

    /**
     * If the counts array is null, then hide the entire nestedRadioButton.
     * Else format the sub-headings of the field that are related to counts.
     * Furthermore, if the counts are non-zero, show the chevron link.
     * @param viewFieldID
     * @param counts
     */
    @Override
    public void setFieldCountsAndVisibility(final ViewFieldID viewFieldID, final @Nullable int[] counts) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NestedRadioButton nestedRadioButton = getNestedRadioButton(viewFieldID);
                if (counts == null) {
                    nestedRadioButton.setVisibility(android.view.View.GONE);
                } else {
                    nestedRadioButton.setVisibility(android.view.View.VISIBLE);
                    int files = counts[0];
                    int dirs = counts[1];
                    nestedRadioButton.setFilesText(files + (files != 1 ? " files" : " file"));
                    nestedRadioButton.setDirsText("in " + dirs + (dirs != 1 ? " dirs" : " dir"));
                    nestedRadioButton.setChevronVisible(files != 0);
                    if (files != 0) {
                        nestedRadioButton.setChevronOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(android.view.View v) {
                                getPresenter().onShowTree(viewFieldID);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void setComparisonAndSyncButton(final boolean comparisonVisible, final boolean syncButtonEnabled) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mComparisonCardView.setVisibility(comparisonVisible ? android.view.View.VISIBLE : android.view.View.GONE);
                mSyncButton.setEnabled(syncButtonEnabled);
            }
        });
    }
    //    @Override
//    public void showError(final String errorMsg) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle( "Error" )
//                        .setMessage(errorMsg)
//                        .setPositiveButton("OK", null)
//                        .show();
//            }
//        });
//    }
//
    @Override
    public void showLoadingState(final boolean show) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (show) {
                    showProgressDialog();
                } else {
                    hideProgressDialog();
                }
            }
        });
    }
    //endregion

    //region Contract.Navigation
    @Override
    public void navigateToBrowseTree(final @Nullable Action action, final DirNode dirNode, final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((AnimatingActivity) getActivity()).useStartAnimations();
                ((AnimatingActivity) getActivity()).startActivity(
                        TreeBrowseFragment.createIntent(getContext(), action, dirNode, title));
            }
        });
    }

    @Override
    public void navigateBack() {
        ((AnimatingActivity) getActivity()).useFinishAnimations();
        navigateExit();
    }
    //endregion

    @OnClick(R.id.sync_button)
    public void onButtonClick() {
        getPresenter().onSyncButtonClicked();
    }

    @Override
    public boolean onBackPressed() {
        getPresenter().onBackClicked();
        return false;
    }

    private NestedRadioButton getNestedRadioButton(ViewFieldID viewFieldID) {
        switch (viewFieldID) {
        case FIELD_FROM_A:
            return mRadioFromA;
        case FIELD_FROM_B:
            return mRadioFromB;
        case FIELD_TO_A:
            return mRadioToA;
        case FIELD_TO_B:
            return mRadioToB;
        case FIELD_ON_A:
            return mRadioOnA;
        case FIELD_ON_B:
            return mRadioOnB;
        case FIELD_NAME_CLASH:
            return mRadioClash;
        }
        return null;
    }
}
