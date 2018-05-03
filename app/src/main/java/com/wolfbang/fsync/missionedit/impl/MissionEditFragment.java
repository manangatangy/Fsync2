package com.wolfbang.fsync.missionedit.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lsmvp.simplemvp.AbstractMvpViewFragment;
import com.lsmvp.simplemvp.ModelUpdater;
import com.lsmvp.simplemvp.ObjectRegistry;
import com.wolfbang.fsync.R;
import com.wolfbang.fsync.application.FsyncApplication;
import com.wolfbang.fsync.missionedit.MissionEditContract.Model;
import com.wolfbang.fsync.missionedit.MissionEditContract.Navigation;
import com.wolfbang.fsync.missionedit.MissionEditContract.Presenter;
import com.wolfbang.fsync.missionedit.MissionEditContract.View;
import com.wolfbang.fsync.missionedit._di.DaggerMissionEditComponent;
import com.wolfbang.fsync.missionedit._di.MissionEditComponent;
import com.wolfbang.fsync.missionedit._di.MissionEditModule;
import com.wolfbang.shared.BackClickHandler;
import com.wolfbang.shared.view.AnimatingActivity;
import com.wolfbang.shared.view.SingleFragActivity;

/**
 * @author david
 * @date 2018-05-03
 */
public class MissionEditFragment
        extends AbstractMvpViewFragment<Presenter, Model, MissionEditComponent>
        implements View,
                   Navigation,
                   BackClickHandler {

    private static final String MCF_MISSION_NAME_DATA = "MCF_MISSION_NAME_DATA";
    private static final String MCF_SCAN_RESULT = "MCF_SCAN_RESULT";

    public static Intent createIntent(Context context) {
        Intent intent = new SingleFragActivity.Builder(context, MissionEditFragment.class.getName())
                .setDisplayHomeAsUpEnabled(true)
                .setTitle("Confirm")
                .build();

        ObjectRegistry objectRegistry = FsyncApplication.getFsyncApplicationComponent().getObjectRegistry();
//        intent.putExtra(MCF_MISSION_NAME_DATA, objectRegistry.put(missionNameData));
//        intent.putExtra(MCF_SCAN_RESULT, objectRegistry.put(scanResult));

        return intent;
    }

    //region SimpleMVP
    @NonNull
    @Override
    protected MissionEditComponent createComponent() {
        return DaggerMissionEditComponent.builder()
                                         .fsyncApplicationComponent(FsyncApplication.getFsyncApplicationComponent())
                                         .missionEditModule(new MissionEditModule())
                                         .build();
    }

    @Override
    protected void doInjection(@NonNull MissionEditComponent component) {
        component.inject(this);
    }

    @NonNull
    @Override
    protected Presenter createPresenter(@NonNull MissionEditComponent component) {
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

//                String key1 = args.getString(MCF_MISSION_NAME_DATA, "");
//                MissionNameData missionNameData = getObjectRegistry().get(key1);
//                model.setMissionNameData(missionNameData);
//
//                String key2 = args.getString(MCF_SCAN_RESULT, "");
//                ScanResult scanResult = getObjectRegistry().get(key2);
//                model.setScanResult(scanResult);
            }
        };
    }
    //endregion

    //region Contract.View
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
    public void navigateBack() {
        ((AnimatingActivity) getActivity()).useFinishAnimations();
        navigateExit();
    }
    //endregion

    @Override
    public boolean onBackPressed() {
        getPresenter().onBackClicked();
        return false;
    }

}
