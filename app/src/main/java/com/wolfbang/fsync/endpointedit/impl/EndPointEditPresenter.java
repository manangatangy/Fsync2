package com.wolfbang.fsync.endpointedit.impl;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.BaseMvpPresenter;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.Model;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.ModelListener;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.Navigation;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.Presenter;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.View;

/**
 * @author david
 * @date 2018-05-03
 */
public class EndPointEditPresenter
        extends BaseMvpPresenter<View, Model, Navigation>
        implements Presenter,
                   ModelListener {

    public EndPointEditPresenter(Model model) {
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
                break;
            default:
                throw new IllegalStateException("Unsupported model state");
            }
        }
    }
    //endregion

    //region EndPointEditContract.Presenter
    @Override
    public void onBackClicked() {
        Navigation navigation = getNavigation();
        if (navigation != null) {
            navigation.navigateBack();
        }
    }
    //endregion

    //region EndPointEditContract.ModelListener
    @Override
    public void onBusyChanged(boolean busy) {
        View view = getView();
        if (view != null) {
            handleBusyChanged(view, busy);
        }
    }
    //endregion

    private void handleBusyChanged(@NonNull View view, boolean busy) {
//        view.showLoadingState(busy);
    }

}
