package com.wolfbang.fsync.emptyclasses.impl;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.BaseMvpPresenter;
import com.wolfbang.fsync.emptyclasses.EmptyClassesContract.Model;
import com.wolfbang.fsync.emptyclasses.EmptyClassesContract.ModelListener;
import com.wolfbang.fsync.emptyclasses.EmptyClassesContract.Navigation;
import com.wolfbang.fsync.emptyclasses.EmptyClassesContract.Presenter;
import com.wolfbang.fsync.emptyclasses.EmptyClassesContract.View;

/**
 * @author david
 * @date 2018-05-03
 */
public class EmptyClassesPresenter
        extends BaseMvpPresenter<View, Model, Navigation>
        implements Presenter,
                   ModelListener {

    public EmptyClassesPresenter(Model model) {
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

    //region EmptyClassesContract.Presenter
    @Override
    public void onBackClicked() {
        Navigation navigation = getNavigation();
        if (navigation != null) {
            navigation.navigateBack();
        }
    }
    //endregion

    //region EmptyClassesContract.ModelListener
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
