package com.wolfbang.fsync.missionconfirm.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.lsmvp.simplemvp.AbstractMvpViewFragment;
import com.lsmvp.simplemvp.ModelUpdater;
import com.lsmvp.simplemvp.ObjectRegistry;
import com.wolfbang.fsync.R;
import com.wolfbang.fsync.application.FsyncApplication;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.mission.EndPoint;
import com.wolfbang.fsync.ftpservice.model.mission.FtpEndPoint;
import com.wolfbang.fsync.ftpservice.model.mission.MissionData;
import com.wolfbang.fsync.ftpservice.model.mission.ScanResult;
import com.wolfbang.fsync.treebrowse.impl.TreeBrowseFragment;
import com.wolfbang.fsync.view.EndPointDetailView;
import com.wolfbang.shared.BackClickHandler;
import com.wolfbang.shared.view.AnimatingActivity;
import com.wolfbang.shared.view.LabelValueRowView;
import com.wolfbang.shared.view.SingleFragActivity;

import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Model;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Navigation;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Presenter;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.View;
import com.wolfbang.fsync.missionconfirm._di.DaggerMissionConfirmComponent;
import com.wolfbang.fsync.missionconfirm._di.MissionConfirmComponent;
import com.wolfbang.fsync.missionconfirm._di.MissionConfirmModule;
import com.wolfbang.shared.view.TextButtonView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author david
 * @date 22 Mar 2018.
 */

public class MissionConfirmFragment
        extends AbstractMvpViewFragment<Presenter, Model, MissionConfirmComponent>
        implements View, Navigation, BackClickHandler {

    private static final String MCF_SCAN_RESULT = "MCF_SCAN_RESULT";

    @BindView(R.id.heading_row_view)
    LabelValueRowView mHeadingRowView;
    @BindView(R.id.text_button_conflict)
    TextButtonView mConflict;
    @BindView(R.id.text_button_copied_1)
    TextButtonView mCopied1;
    @BindView(R.id.text_button_copied_2)
    TextButtonView mCopied2;
    @BindView(R.id.text_button_overridden_1)
    TextButtonView mOverridden1;
    @BindView(R.id.text_button_overridden_2)
    TextButtonView mOverridden2;
    @BindView(R.id.sync_button)
    Button mSyncButton;

    public static Intent createIntent(Context context, ScanResult scanResult) {
        Intent intent = new SingleFragActivity.Builder(context, MissionConfirmFragment.class.getName())
                .setDisplayHomeAsUpEnabled(true)
                .setTitle("Confirm")
                .build();

        ObjectRegistry objectRegistry = FsyncApplication.getFsyncApplicationComponent().getObjectRegistry();
        String key = objectRegistry.put(scanResult);
        intent.putExtra(MCF_SCAN_RESULT, key);

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
    }

    @Nullable
    @Override
    protected ModelUpdater<Model> getModelInitializer() {
        return new ModelUpdater<Model>() {
            @Override
            public void updateModel(Model model) {
                Bundle args = getArguments();

                String key = args.getString(MCF_SCAN_RESULT, "");
                ScanResult scanResult = getObjectRegistry().get(key);
                model.setScanResult(scanResult);
            }
        };
    }
    //endregion

    //region Contract.View
    @Override
    public void setMissionName(String missionName) {
        mHeadingRowView.setValue(missionName);
    }

    @Override
    public void setConflict(@Nullable final String text, final boolean buttonEnabled) {
        setTextButtonView(mConflict, text, "Resolve", buttonEnabled);
    }

    @Override
    public void setCopied1(@Nullable final String text, final boolean buttonEnabled) {
        setTextButtonView(mCopied1, text, "Review", buttonEnabled);
    }

    @Override
    public void setCopied2(@Nullable final String text, final boolean buttonEnabled) {
        setTextButtonView(mCopied2, text, "Review", buttonEnabled);
    }

    @Override
    public void setOverridden1(@Nullable final String text, final boolean buttonEnabled) {
        setTextButtonView(mOverridden1, text, "Review", buttonEnabled);
    }

    @Override
    public void setOverridden2(@Nullable final String text, final boolean buttonEnabled) {
        setTextButtonView(mOverridden2, text, "Review", buttonEnabled);
    }

    @Override
    public void setSyncButtonEnabled(final boolean enabled) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSyncButton.setEnabled(enabled);
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
//    @Override
//    public void navigateToBrowseTree(final DirNode dirNode) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                ((AnimatingActivity) getActivity()).useStartAnimations();
//                startActivity(TreeBrowseFragment.createIntent(getContext(), dirNode));
//            }
//        });
//    }

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

    private void setTextButtonView(final TextButtonView textButtonView,
                                   @Nullable final String text,
                                   @Nullable final String buttonText,
                                   final boolean buttonEnabled) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textButtonView.setVisibility(text == null ? android.view.View.GONE : android.view.View.VISIBLE);
                textButtonView.setLabel(text);
                textButtonView.setButton(buttonText);
                textButtonView.setButtonEnabled(buttonEnabled);
            }
        });
    }

}
