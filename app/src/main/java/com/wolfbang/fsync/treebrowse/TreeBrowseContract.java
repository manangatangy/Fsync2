package com.wolfbang.fsync.treebrowse;

import com.lsmvp.simplemvp.AbstractMvpContract;

/**
 * @author david
 * @date 19 Mar 2018.
 */

public interface TreeBrowseContract extends AbstractMvpContract {

    interface Navigation extends BasicNavigation {
    }

    interface View extends AbstractView {
//        void setSomeField(String someValue);
//        void showError();
//        void showLoadingState(boolean show);
    }

    interface Presenter extends AbstractPresenter<View, Model, Navigation> {
//        void onSomeButtonClicked(int timePeriod);
        void onBackClicked();
    }

    interface Model extends AbstractModel {
//        void setFeature2Data(Feature2Data feature2Data);
//        Feature2Data getFeature2Data();
//
//        String getSomeValue();
//        void doSomeAction(int timePeriod);
//
//        boolean isBusy();
//        ModelState getModelState();
    }

    enum ModelState {
        IDLE,
        ERROR,
        SUCCESS
    }
    interface ModelListener extends AbstractModelListener {
//        void onBusyChanged(boolean busy);
//        void onRetrieveSomeResult(@NonNull String resultValue);
//        void onRetrieveFailed();
    }
}
