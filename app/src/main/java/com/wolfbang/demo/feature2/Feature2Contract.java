package com.wolfbang.demo.feature2;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.AbstractMvpContract;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public interface Feature2Contract extends AbstractMvpContract {

    interface Navigation extends BasicNavigation {
    }

    interface View extends AbstractView {
        void setSomeField(String someValue);
        void showError();
    }

    interface Presenter extends AbstractPresenter<View, Model, Navigation> {
        void onSomeButtonClicked();
        void onBackClicked();
    }

    interface Model extends AbstractModel {
        void setSomeValue(String someValue);
        String getSomeValue();
        void doSomeAction();

        boolean isBusy();
        ModelState getModelState();
    }

    enum ModelState {
        IDLE,
        ERROR,
        SUCCESS
    }
    interface ModelListener extends AbstractModelListener {
        void onBusyChanged(boolean busy);
        void onRetrieveSomeResult(@NonNull String resultValue);
        void onRetrieveFailed();
    }
}
