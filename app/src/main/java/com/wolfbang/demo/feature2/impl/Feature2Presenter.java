package com.wolfbang.demo.feature2.impl;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.BaseMvpPresenter;
import com.wolfbang.demo.feature2.Feature2Contract.View;
import com.wolfbang.demo.feature2.Feature2Contract.Model;
import com.wolfbang.demo.feature2.Feature2Contract.Navigation;
import com.wolfbang.demo.feature2.Feature2Contract.ModelListener;
import com.wolfbang.demo.feature2.Feature2Contract.Presenter;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public class Feature2Presenter
        extends BaseMvpPresenter<View, Model, Navigation>
        implements Presenter, ModelListener {

    public Feature2Presenter(Model model) {
        super(model);
        model.setListener(this);
    }

    //region SimpleMVP
    @Override
    protected void refreshView(@NonNull View view) {
        Model model = getModel();
        boolean busy = model.isBusy();
        handleBusyChanged(view, busy);

        if (!busy) {
            switch (model.getModelState()) {
                case IDLE:
                    // Display the initial value of the model value.
                case SUCCESS:
                    handleSuccess(view, model.getSomeValue());
                    break;
                case ERROR:
                    handleError(view);
                    break;
                default:
                    throw new IllegalStateException("Unsupported model state");
            }
        }
    }
    //endregion

    //region MainContract.Presenter
    @Override
    public void onSomeButtonClicked() {
        getModel().doSomeAction();
    }

    @Override
    public void onBackClicked() {
        Navigation navigation = getNavigation();
        if (navigation != null) {
            navigation.navigateExit();
        }
    }
    //endregion

    //region ConfirmTermContract.ModelListener
    @Override
    public void onBusyChanged(boolean busy) {
        View view = getView();
        if (view != null) {
            handleBusyChanged(view, busy);
        }
    }

    @Override
    public void onRetrieveSomeResult(@NonNull String resultValue) {

    }

    @Override
    public void onRetrieveFailed() {

    }
    //endregion

    private void handleSuccess(@NonNull View view, @NonNull String resultValue) {
        view.setSomeField(resultValue);
    }

    private void handleError(@NonNull View view) {
        view.showError();
    }

    private void handleBusyChanged(@NonNull View view, boolean busy) {
        if (busy) {
            view.showProgressDialog();
        } else {
            view.hideProgressDialog();
        }
    }
}
