package com.wolfbang.fsync.endpointedit.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lsmvp.simplemvp.AbstractMvpViewFragment;
import com.lsmvp.simplemvp.ModelUpdater;
import com.lsmvp.simplemvp.ObjectRegistry;
import com.wolfbang.fsync.R;
import com.wolfbang.fsync.application.FsyncApplication;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.Model;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.Navigation;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.Presenter;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.View;
import com.wolfbang.fsync.endpointedit._di.DaggerEndPointEditComponent;
import com.wolfbang.fsync.endpointedit._di.EndPointEditComponent;
import com.wolfbang.fsync.endpointedit._di.EndPointEditModule;
import com.wolfbang.shared.BackClickHandler;
import com.wolfbang.shared.view.AnimatingActivity;
import com.wolfbang.shared.view.NestedRadioGroup;
import com.wolfbang.shared.view.RadioLayout;
import com.wolfbang.shared.view.SingleFragActivity;

import butterknife.BindView;

/**
 * @author david
 * @date 2018-05-03
 */
public class EndPointEditFragment
        extends AbstractMvpViewFragment<Presenter, Model, EndPointEditComponent>
        implements View,
                   Navigation,
                   BackClickHandler {

    private static final String MCF_MISSION_NAME_DATA = "MCF_MISSION_NAME_DATA";
    private static final String MCF_SCAN_RESULT = "MCF_SCAN_RESULT";

    @BindView(R.id.text_input_layout_name)
    TextInputLayout mInputLayoutName;
    @BindView(R.id.edit_text_name)
    EditText mEditTextName;

    @BindView(R.id.image_chevron)
    ImageView mImageChevron;

    @BindView(R.id.radio_group_type)
    NestedRadioGroup mRadioGroup;
    @BindView(R.id.radio_ftp)
    RadioLayout mRadioFtp;
    @BindView(R.id.radio_dummy)
    RadioLayout mRadioDummy;

    @BindView(R.id.layout_ftp)
    LinearLayout mLayoutFtp;

    @BindView(R.id.text_input_layout_host)
    TextInputLayout mInputLayoutHost;
    @BindView(R.id.edit_text_host)
    EditText mEditTextHost;

    @BindView(R.id.text_input_layout_user)
    TextInputLayout mInputLayoutUser;
    @BindView(R.id.edit_text_user)
    EditText mEditTextUser;

    @BindView(R.id.text_input_layout_password)
    TextInputLayout mInputLayoutPassword;
    @BindView(R.id.edit_text_password)
    EditText mEditTextPassword;

    @BindView(R.id.text_input_layout_directory)
    TextInputLayout mInputLayoutRootDir;
    @BindView(R.id.edit_text_directory)
    EditText mEditTextRootDir;

    @BindView(R.id.ok_button)
    Button mButtonOk;
    @BindView(R.id.cancel_button)
    Button mButtonCancel;

    public static Intent createIntent(Context context) {
        Intent intent = new SingleFragActivity.Builder(context, EndPointEditFragment.class.getName())
                .setDisplayHomeAsUpEnabled(true)
                .setTitle("Edit End Point")
                .build();

        ObjectRegistry objectRegistry = FsyncApplication.getFsyncApplicationComponent().getObjectRegistry();
//        intent.putExtra(MCF_MISSION_NAME_DATA, objectRegistry.put(missionNameData));
//        intent.putExtra(MCF_SCAN_RESULT, objectRegistry.put(scanResult));

        return intent;
    }

    //region SimpleMVP
    @NonNull
    @Override
    protected EndPointEditComponent createComponent() {
        return DaggerEndPointEditComponent.builder()
                                          .fsyncApplicationComponent(FsyncApplication.getFsyncApplicationComponent())
                                          .endPointEditModule(new EndPointEditModule())
                                          .build();
    }

    @Override
    protected void doInjection(@NonNull EndPointEditComponent component) {
        component.inject(this);
    }

    @NonNull
    @Override
    protected Presenter createPresenter(@NonNull EndPointEditComponent component) {
        return component.providePresenter();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_end_point_edit;
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
