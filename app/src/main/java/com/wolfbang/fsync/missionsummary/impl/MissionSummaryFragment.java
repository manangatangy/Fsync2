package com.wolfbang.fsync.missionsummary.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lsmvp.simplemvp.AbstractMvpViewFragment;
import com.lsmvp.simplemvp.ModelUpdater;
import com.lsmvp.simplemvp.ObjectRegistry;
import com.wolfbang.fsync.R;
import com.wolfbang.fsync.application.FsyncApplication;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Model;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Presenter;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.View;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Navigation;
import com.wolfbang.fsync.missionsummary._di.DaggerMissionSummaryComponent;
import com.wolfbang.fsync.missionsummary._di.MissionSummaryComponent;
import com.wolfbang.fsync.missionsummary._di.MissionSummaryModule;
import com.wolfbang.shared.BackClickHandler;
import com.wolfbang.shared.SingleFragActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public class MissionSummaryFragment
        extends AbstractMvpViewFragment<Presenter, Model, MissionSummaryComponent>
        implements View, Navigation, BackClickHandler {

    private static final String MSF_FEATURE1_DATA = "MSF_FEATURE1_DATA";

    @BindView(R.id.feature1_button)
    Button mButton;
    @BindView(R.id.feature1_textView)
    TextView mTextView;
    @BindView(R.id.feature1_editText)
    EditText mEditText;

    public static Intent createIntent(Context context, MissionSummaryData missionSummaryData) {
        Intent intent = new SingleFragActivity.Builder(context, MissionSummaryFragment.class.getName())
                .setDisplayHomeAsUpEnabled(true)
                .setTitle("Mission")
                .build();

        ObjectRegistry objectRegistry = FsyncApplication.getFsyncApplicationComponent().getObjectRegistry();
        String key = objectRegistry.put(missionSummaryData);
        intent.putExtra(MSF_FEATURE1_DATA, key);

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

                String key = args.getString(MSF_FEATURE1_DATA, "");
                MissionSummaryData missionSummaryData = getObjectRegistry().get(key);
                model.setMissionSummaryData(missionSummaryData);
            }
        };
    }
    //endregion

    //region MissionSummaryContract.View
    @Override
    public void setSomeField(final String someValue) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(someValue);
            }
        });
    }

    @Override
    public void showError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle( "Error" )
                        .setMessage("some message")
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

    @OnClick(R.id.feature1_button)
    public void onButtonClick() {
        try {
            getPresenter().onSomeButtonClicked(Integer.parseInt(mEditText.getText().toString()));
        } catch (NumberFormatException nfe) {
        }
    }

    @Override
    public boolean onBackPressed() {
        getPresenter().onBackClicked();
        return false;
    }

}
