package com.wolfbang.fsync.feature2.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lsmvp.simplemvp.AbstractMvpViewActivity;
import com.lsmvp.simplemvp.ModelUpdater;
import com.lsmvp.simplemvp.NestedViewHost;
import com.lsmvp.simplemvp.ObjectRegistry;
import com.lsmvp.simplemvp.ProgressDialogProvider;
import com.wolfbang.fsync.R;
import com.wolfbang.fsync.application.FsyncApplication;
import com.wolfbang.fsync.feature2.Feature2Contract.Model;
import com.wolfbang.fsync.feature2.Feature2Contract.Presenter;
import com.wolfbang.fsync.feature2.Feature2Contract.View;
import com.wolfbang.fsync.feature2.Feature2Contract.Navigation;
import com.wolfbang.fsync.feature2._di.DaggerFeature2Component;
import com.wolfbang.fsync.feature2._di.Feature2Component;
import com.wolfbang.fsync.feature2._di.Feature2Module;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public class Feature2Activity
        extends AbstractMvpViewActivity<Presenter, Model, Feature2Component>
        implements View, Navigation, NestedViewHost, ProgressDialogProvider {

    private static final String KEY_FEATURE2_DATA = "KEY_FEATURE2_DATA";

    @BindView(R.id.feature2_button)
    Button mButton;
    @BindView(R.id.feature2_textView)
    TextView mTextView;
    @BindView(R.id.feature2_editText)
    EditText mEditText;

    @BindView(R.id.progress_bar_frame)
    FrameLayout mProgressBarLayout;

    @NonNull
    public static Intent createIntent(Context context, Feature2Data feature2Data) {
        Intent intent = new Intent(context, Feature2Activity.class);
        ObjectRegistry objectRegistry = FsyncApplication.getFsyncApplicationComponent().getObjectRegistry();
        String key = objectRegistry.put(feature2Data);
        intent.putExtra(KEY_FEATURE2_DATA, key);

        return intent;
    }

    //region SimpleMVP
    @NonNull
    @Override
    protected Feature2Component createComponent() {
        return DaggerFeature2Component.builder()
                .fsyncApplicationComponent(FsyncApplication.getFsyncApplicationComponent())
                .feature2Module(new Feature2Module())
                .build();
    }

    @Override
    protected void doInjection(@NonNull Feature2Component component) {
        super.doInjection(component);
        component.inject(this);
    }

    @NonNull
    @Override
    protected Presenter createPresenter(@NonNull Feature2Component component) {
        return component.providePresenter();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_feature2;
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
                Bundle args = getIntent().getExtras();

                String key = args.getString(KEY_FEATURE2_DATA, "");
                Feature2Data feature2Data = getObjectRegistry().get(key);
                model.setFeature2Data(feature2Data);
            }
        };
    }
    //endregion

    //region Android framework stuff
    //implement onOptionsItemSelected, onActivityResult, etc as desired.
    //endregion

    //region Feature2Contract.View
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Feature2Activity.this);
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

    @OnClick(R.id.feature2_button)
    public void onButtonClick() {
        try {
            getPresenter().onSomeButtonClicked(Integer.parseInt(mEditText.getText().toString()));
        } catch (NumberFormatException nfe) {
        }
    }

    //region ProgressDialogProvider
    @Override
    public void showProgressDialog() {
        mProgressBarLayout.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showProgressDialog(int messageTemplateId, Object... formatArgs) {
        showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        mProgressBarLayout.setVisibility(android.view.View.GONE);
    }

    @Override
    public boolean isProgressVisible() {
        return (mProgressBarLayout.getVisibility() == android.view.View.VISIBLE);
    }
    //endregion

}
