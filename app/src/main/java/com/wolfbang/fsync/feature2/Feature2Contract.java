package com.wolfbang.fsync.feature2;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.AbstractMvpContract;
import com.wolfbang.fsync.feature2.impl.Feature2Data;

/**
 * @author david
 * @date 10 Mar 2018.
 * This is straight copy of Feature1Contract as are the Model and Presenter.
 *
 */

public interface Feature2Contract extends AbstractMvpContract {

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
        void setFeature2Data(Feature2Data feature2Data);
        Feature2Data getFeature2Data();

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
