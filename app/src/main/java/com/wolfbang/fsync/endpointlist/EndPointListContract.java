package com.wolfbang.fsync.endpointlist;

import com.lsmvp.simplemvp.AbstractMvpContract;

/**
 * @author david
 * @date 2018-05-03
 */

public interface EndPointListContract {

    interface Navigation extends AbstractMvpContract.BasicNavigation {
        void navigateBack();
    }

    interface View extends AbstractMvpContract.AbstractView {
        void showLoadingState(boolean show);
    }

    interface Presenter extends AbstractMvpContract.AbstractPresenter<View, Model, Navigation> {
        void onBackClicked();
    }

    interface Model extends AbstractMvpContract.AbstractModel {
        boolean isBusy();
        ModelState getModelState();
        void resetModelState();
    }

    enum ModelState {
        IDLE
    }

    interface ModelListener extends AbstractMvpContract.AbstractModelListener {
        void onBusyChanged(boolean busy);
    }
}
