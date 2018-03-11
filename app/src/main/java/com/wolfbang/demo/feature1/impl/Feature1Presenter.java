package com.wolfbang.demo.feature1.impl;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.BaseMvpPresenter;
import com.wolfbang.demo.feature1.Feature1Contract.View;
import com.wolfbang.demo.feature1.Feature1Contract.Model;
import com.wolfbang.demo.feature1.Feature1Contract.Navigation;
import com.wolfbang.demo.feature1.Feature1Contract.ModelListener;
import com.wolfbang.demo.feature1.Feature1Contract.Presenter;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public class Feature1Presenter
        extends BaseMvpPresenter<View, Model, Navigation>
        implements Presenter, ModelListener {

    public Feature1Presenter(Model model) {
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

    //region Feature1Contract.Presenter
    @Override
    public void onSomeButtonClicked(int timePeriod) {
        getModel().doSomeAction(timePeriod);
    }

    @Override
    public void onBackClicked() {
        Navigation navigation = getNavigation();
        if (navigation != null) {
            navigation.navigateExit();
        }
    }
    //endregion

    //region Feature1Contract.ModelListener
    @Override
    public void onBusyChanged(boolean busy) {
        View view = getView();
        if (view != null) {
            handleBusyChanged(view, busy);
        }
    }

    @Override
    public void onRetrieveSomeResult(@NonNull String resultValue) {
        View view = getView();
        if (view != null) {
            handleSuccess(view, resultValue);
        }
    }

    @Override
    public void onRetrieveFailed() {
        View view = getView();
        if (view != null) {
            handleError(view);
        }
    }
    //endregion

    private void handleSuccess(@NonNull View view, @NonNull String resultValue) {
        view.setSomeField(resultValue);
    }

    private void handleError(@NonNull View view) {
        view.showError();
    }

    private void handleBusyChanged(@NonNull View view, boolean busy) {
        view.showLoadingState(busy);
    }
}
