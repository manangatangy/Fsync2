/*
 *  (C) 2017. National Australia Bank [All rights reserved]. This product and related documentation are protected by
 *  copyright restricting its use, copying, distribution, and decompilation. No part of this product or related
 *  documentation may be reproduced in any form by any means without prior written authorization of National Australia
 *  Bank. Unless otherwise arranged, third parties may not have access to this product or related documents.
 *
 */

package com.lsmvp.simplemvp;


import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Fundamental Simple MVP contract
 * @author Chris James
 * @date 2 Mar 2017
 */
public interface AbstractMvpContract {

    /**
     * Navigation; abstract is empty to allow diverging (special cases)
     */
    interface AbstractNavigation {
    }

    /**
     * Basic Navigation
     */
    interface BasicNavigation extends AbstractNavigation {
        void navigateExit();
    }

    interface AbstractView {

        void hideProgressDialog();
        void showProgressDialog();
        void showProgressDialog(final @StringRes int messageTemplateId, final Object... formatArgs);
        boolean isProgressVisible();

        void hideSoftKeyboard();
    }


    interface AbstractPresenter<ViewT extends AbstractView, ModelT extends AbstractModel, NavigationT extends AbstractNavigation> {
        /**
         * Update presenter's view binding
         */
        void bindMvpView(@Nullable ViewT view);

        /**
         * Update presenter's navigation binding (normally provided by an activity or fragment)
         */
        void bindMvpNavigation(@Nullable NavigationT navigation);

        /**
         * Invoked by the Android lifecycle after injection, but before first use of the model.
         */
        void initializeModel(ModelUpdater<ModelT> modelInitializer);

        /**
         * Invoked by the Android lifecycle on resume
         */
        void updateModel(ModelUpdater<ModelT> modelUpdater);

        /**
         * Called once on construction of a new SMVP stack.
         * You can use this to perform one-off tasks within the presenter (eg feature analytics)
         */
        void onInitialize();

        /**
         * Ask presenter to update view with current state
         */
        void refreshView();

    }

    interface AbstractModel {
        <ListenerT extends AbstractModelListener> void setListener(ListenerT listener);
        <ListenerT extends AbstractModelListener> ListenerT getListener();
//        boolean isSessionValid();
    }

    interface AbstractModelListener {
    }
}