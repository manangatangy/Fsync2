package com.wolfbang.fsync.missionsummary.impl;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.BaseMvpPresenter;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.View;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Model;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Navigation;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.ModelListener;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Presenter;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public class MissionSummaryPresenter
        extends BaseMvpPresenter<View, Model, Navigation>
        implements Presenter, ModelListener {

    public MissionSummaryPresenter(Model model) {
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
                    handleError(view, model.getErrorMsg());
                    break;
                default:
                    throw new IllegalStateException("Unsupported model state");
            }
        }
    }
    //endregion

    //region MissionSummaryContract.Presenter
    @Override
    public void onSomeButtonClicked(String path) {
        getModel().doSomeAction(path);
    }

    @Override
    public void onBackClicked() {
        Navigation navigation = getNavigation();
        if (navigation != null) {
            navigation.navigateExit();
        }
    }
    //endregion

    //region MissionSummaryContract.ModelListener
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
    public void onRetrieveFailed(String errorMsg) {
        View view = getView();
        if (view != null) {
            handleError(view, errorMsg);
        }
    }
    //endregion

    private void handleSuccess(@NonNull View view, @NonNull String resultValue) {
        view.setSomeField(resultValue);
    }

    private void handleError(@NonNull View view, String errorMsg) {
        view.showError(errorMsg);
    }

    private void handleBusyChanged(@NonNull View view, boolean busy) {
        view.showLoadingState(busy);
    }
}
