package com.wolfbang.demo.feature2.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.lsmvp.simplemvp.AbstractMvpViewActivity;
import com.lsmvp.simplemvp.ModelUpdater;
import com.wolfbang.demo.R;
import com.wolfbang.demo.application.MyApplication;
import com.wolfbang.demo.feature2.Feature2Contract.Model;
import com.wolfbang.demo.feature2.Feature2Contract.Presenter;
import com.wolfbang.demo.feature2.Feature2Contract.View;
import com.wolfbang.demo.feature2.Feature2Contract.Navigation;
import com.wolfbang.demo.feature2._di.DaggerFeature2Component;
import com.wolfbang.demo.feature2._di.Feature2Component;
import com.wolfbang.demo.feature2._di.Feature2Module;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public class Feature2Activity
        extends AbstractMvpViewActivity<Presenter, Model, Feature2Component>
        implements View, Navigation
//       , BackPressListener
{

    //region SimpleMVP
    @NonNull
    @Override
    protected Feature2Component createComponent() {
        return DaggerFeature2Component.builder()
                .myApplicationComponent(MyApplication.getMyApplicationComponent())
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
        return R.layout.activity_main;
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
//                Bundle args = getArguments();       // for frag

//                DepositAccount depositAccount = getObjectRegistry().get(args.getString(KEY_CONFIRM_TERM_ACCOUNT, ""));
//                model.setDepositAccount(depositAccount);

//                Date startDate = getObjectRegistry().get(args.getString(KEY_CONFIRM_TERM_START_DATE, ""));
//                model.setStartDate(startDate);

//                RateModel rateModel = getObjectRegistry().get(args.getString(KEY_CONFIRM_TERM_RATE_MODEL, ""));
//                model.setRateModel(rateModel);
            }
        };
    }
    //endregion

    //region Android framework stuff
    @Override
    public void onResume() {
        super.onResume();
//        setHasOptionsMenu(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
// for fragments
//        inflater.inflate(R.menu.menu_term_deposit_confirm_term, menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_term_details_confirm_term_btn_confirm:
//                onClickConfirmButton();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    //endregion

    //region MainContract.View
    @Override
    public void setSomeField(String someValue) {

    }

    @Override
    public void showError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

        builder.setTitle( "Error" )
                .setMessage("some message")
                .show();
    }
    //endregion

}
