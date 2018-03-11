package com.wolfbang.demo.feature1.impl;

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
import com.wolfbang.demo.R;
import com.wolfbang.demo.application.MyApplication;
import com.wolfbang.demo.feature1.Feature1Contract.Model;
import com.wolfbang.demo.feature1.Feature1Contract.Presenter;
import com.wolfbang.demo.feature1.Feature1Contract.View;
import com.wolfbang.demo.feature1.Feature1Contract.Navigation;
import com.wolfbang.demo.feature1._di.DaggerFeature1Component;
import com.wolfbang.demo.feature1._di.Feature1Component;
import com.wolfbang.demo.feature1._di.Feature1Module;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public class Feature1Fragment
        extends AbstractMvpViewFragment<Presenter, Model, Feature1Component>
        implements View, Navigation {

    private static final String KEY_FEATURE1_DATA = "KEY_FEATURE1_DATA";

    @BindView(R.id.feature1_button)
    Button mButton;
    @BindView(R.id.feature1_textView)
    TextView mTextView;
    @BindView(R.id.feature1_editText)
    EditText mEditText;

    public static Intent createIntent(Context context, Feature1Data feature1Data) {
        Intent intent = Feature1Activity.createIntent(context, Feature1Fragment.class.getName());

        ObjectRegistry objectRegistry = MyApplication.getMyApplicationComponent().getObjectRegistry();
        String key = objectRegistry.put(feature1Data);
        intent.putExtra(KEY_FEATURE1_DATA, key);

        return intent;
    }

    //region SimpleMVP
    @NonNull
    @Override
    protected Feature1Component createComponent() {
        return DaggerFeature1Component.builder()
                .myApplicationComponent(MyApplication.getMyApplicationComponent())
                .feature1Module(new Feature1Module())
                .build();
    }

    @Override
    protected void doInjection(@NonNull Feature1Component component) {
        component.inject(this);
    }

    @NonNull
    @Override
    protected Presenter createPresenter(@NonNull Feature1Component component) {
        return component.providePresenter();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_feature1;
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

                String key = args.getString(KEY_FEATURE1_DATA, "");
                Feature1Data feature1Data = getObjectRegistry().get(key);
                model.setFeature1Data(feature1Data);
            }
        };
    }
    //endregion

    //region Android framework stuff
    //implement onOptionsItemSelected, onActivityResult, etc as desired.
    //endregion

    //region Feature1Contract.View
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

}
