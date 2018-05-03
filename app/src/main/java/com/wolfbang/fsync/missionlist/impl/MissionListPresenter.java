package com.wolfbang.fsync.missionlist.impl;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.BaseMvpPresenter;
import com.wolfbang.fsync.missionlist.MissionListContract.Model;
import com.wolfbang.fsync.missionlist.MissionListContract.ModelListener;
import com.wolfbang.fsync.missionlist.MissionListContract.Navigation;
import com.wolfbang.fsync.missionlist.MissionListContract.Presenter;
import com.wolfbang.fsync.missionlist.MissionListContract.View;

/**
 * @author david
 * @date 2018-05-03
 */
public class MissionListPresenter
        extends BaseMvpPresenter<View, Model, Navigation>
        implements Presenter,
                   ModelListener {

    public MissionListPresenter(Model model) {
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

    //region MissionListContract.Presenter
    @Override
    public void onBackClicked() {
        Navigation navigation = getNavigation();
        if (navigation != null) {
            navigation.navigateBack();
        }
    }
    //endregion

    //region MissionListContract.ModelListener
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
