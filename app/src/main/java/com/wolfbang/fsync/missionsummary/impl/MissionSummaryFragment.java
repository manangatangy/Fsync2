package com.wolfbang.fsync.missionsummary.impl;

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
import com.wolfbang.fsync.ftpservice.model.mission.EndPoint;
import com.wolfbang.fsync.ftpservice.model.mission.FtpEndPoint;
import com.wolfbang.fsync.ftpservice.model.mission.MissionNameData;
import com.wolfbang.fsync.ftpservice.model.mission.ScanResult;
import com.wolfbang.fsync.missionconfirm.impl.MissionConfirmFragment;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Model;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Navigation;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Presenter;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.View;
import com.wolfbang.fsync.missionsummary._di.DaggerMissionSummaryComponent;
import com.wolfbang.fsync.missionsummary._di.MissionSummaryComponent;
import com.wolfbang.fsync.missionsummary._di.MissionSummaryModule;
import com.wolfbang.fsync.view.EndPointDetailView;
import com.wolfbang.shared.BackClickHandler;
import com.wolfbang.shared.view.AnimatingActivity;
import com.wolfbang.shared.view.LabelValueRowView;
import com.wolfbang.shared.view.SingleFragActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public class MissionSummaryFragment
        extends AbstractMvpViewFragment<Presenter, Model, MissionSummaryComponent>
        implements View, Navigation, BackClickHandler {

    private static final String MSF_MISSION_DATA = "MSF_MISSION_DATA";

    @BindView(R.id.heading_row_view)
    LabelValueRowView mHeadingRowView;
    @BindView(R.id.end_point_view_a)
    EndPointDetailView mEndPointA;
    @BindView(R.id.end_point_view_b)
    EndPointDetailView mEndPointB;
    @BindView(R.id.scan_button)
    Button mScanButton;

    public static Intent createIntent(Context context, MissionNameData missionNameData) {
        Intent intent = new SingleFragActivity.Builder(context, MissionSummaryFragment.class.getName())
                .setDisplayHomeAsUpEnabled(true)
                .setTitle("Summary")
                .build();

        ObjectRegistry objectRegistry = FsyncApplication.getFsyncApplicationComponent().getObjectRegistry();
        String key = objectRegistry.put(missionNameData);
        intent.putExtra(MSF_MISSION_DATA, key);

        return intent;
    }

    //region SimpleMVP
    @NonNull
    @Override
    protected MissionSummaryComponent createComponent() {
        return DaggerMissionSummaryComponent.builder()
                .fsyncApplicationComponent(FsyncApplication.getFsyncApplicationComponent())
                .missionSummaryModule(new MissionSummaryModule())
                .build();
    }

    @Override
    protected void doInjection(@NonNull MissionSummaryComponent component) {
        component.inject(this);
    }

    @NonNull
    @Override
    protected Presenter createPresenter(@NonNull MissionSummaryComponent component) {
        return component.providePresenter();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mission_summary;
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

                String key = args.getString(MSF_MISSION_DATA, "");
                MissionNameData missionNameData = getObjectRegistry().get(key);
                model.setMissionNameData(missionNameData);
            }
        };
    }
    //endregion

    //region MissionSummaryContract.View
    @Override
    public void setMissionName(String missionName) {
        mHeadingRowView.setValue(missionName);
    }

    @Override
    public void setEndPointDetailsA(EndPoint endPoint) {
        FtpEndPoint ftpEndPoint = (FtpEndPoint)endPoint;
        setEndPointDetails("A", ftpEndPoint, mEndPointA);
    }

    @Override
    public void setEndPointDetailsB(EndPoint endPoint) {
        FtpEndPoint ftpEndPoint = (FtpEndPoint)endPoint;
        setEndPointDetails("B", ftpEndPoint, mEndPointB);
    }

    @Override
    public void showError(final String errorMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle( "Error" )
                        .setMessage(errorMsg)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }

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

    //region MissionSummaryContract.Navigation
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
    public void navigateToMissionConfirm(final MissionNameData missionNameData, final ScanResult scanResult) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((AnimatingActivity) getActivity()).useStartAnimations();
                ((AnimatingActivity) getActivity()).startActivity(MissionConfirmFragment.createIntent(getContext(), missionNameData, scanResult));
            }
        });
    }

    @Override
    public void navigateBack() {
        ((AnimatingActivity) getActivity()).useFinishAnimations();
        navigateExit();
    }
    //endregion

    @OnClick(R.id.scan_button)
    public void onButtonClick() {
        getPresenter().onScanButtonClicked();
    }

    @Override
    public boolean onBackPressed() {
        getPresenter().onBackClicked();
        return false;
    }

    private void setEndPointDetails(String name, FtpEndPoint endPoint, EndPointDetailView view) {
        view.getHeadingRowView().setLabel("End Point " + name);
        view.getHeadingRowView().setValue(endPoint.getEndPointName());
        view.getHostNameRowView().setValue(endPoint.getHost());
        view.getUserNameRowView().setValue(endPoint.getUserName());
        view.getPasswordRowView().setValue(endPoint.getPassword());
        view.getRootDirRowView().setValue(endPoint.getRootDir());
    }

}
