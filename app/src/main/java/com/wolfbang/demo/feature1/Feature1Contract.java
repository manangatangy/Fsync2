package com.wolfbang.demo.feature1;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.AbstractMvpContract;
import com.wolfbang.demo.feature1.impl.Feature1Data;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public interface Feature1Contract extends AbstractMvpContract {

    interface Navigation extends BasicNavigation {
    }

    interface View extends AbstractView {
        void setSomeField(String someValue);
        void showError();
        void showLoadingState(boolean show);
    }

    interface Presenter extends AbstractPresenter<View, Model, Navigation> {
        void onSomeButtonClicked(int timePeriod);
        void onBackClicked();
    }

    interface Model extends AbstractModel {
        void setFeature1Data(Feature1Data feature1Data);
        Feature1Data getFeature1Data();

        String getSomeValue();
        void doSomeAction(int timePeriod);

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
