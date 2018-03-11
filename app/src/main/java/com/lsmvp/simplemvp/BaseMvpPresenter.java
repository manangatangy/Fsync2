/*
 *  (C) 2017. National Australia Bank [All rights reserved]. This product and related documentation are protected by
 *  copyright restricting its use, copying, distribution, and decompilation. No part of this product or related
 *  documentation may be reproduced in any form by any means without prior written authorization of National Australia
 *  Bank. Unless otherwise arranged, third parties may not have access to this product or related documents.
 *
 */

package com.lsmvp.simplemvp;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import com.lsmvp.simplemvp.AbstractMvpContract.AbstractModel;
import com.lsmvp.simplemvp.AbstractMvpContract.AbstractModelListener;
import com.lsmvp.simplemvp.AbstractMvpContract.AbstractNavigation;
import com.lsmvp.simplemvp.AbstractMvpContract.AbstractView;


/**
 * Base implementation of Simple MVP presenter. All presenters should be pure java. If you find yourself
 * wanting to pass an Android object to the presenter it probably means that object should be passed to the Model, and
 * probably via an isolating interface (facade).
 * @author Chris James
 * @date 2 Mar 2017
 */

/**
 * Base implementation of Simple MVP presenter. All presenters should be pure java. If you find yourself
 * wanting to pass an Android object to the presenter it probably means that object should be passed to the Model, and
 * probably via an isolating interface (facade).
 *
 * @author Chris James
 * @date 2 Mar 2017
 */

public abstract class BaseMvpPresenter<ViewT extends AbstractView, ModelT extends AbstractModel, NavigationT extends AbstractNavigation>
        implements AbstractMvpContract.AbstractPresenter<ViewT, ModelT, NavigationT>,
                   AbstractModelListener {

    // Reference to view is weak; we do not own it nor should we depend on it being set..
    private WeakReference<ViewT> mView = new WeakReference<>(null);

    private WeakReference<NavigationT> mNavigation = new WeakReference<>(null);

    // We own the model. It is safe for us to have a strong reference to it, but it should not hold a strong reference
    // to us.
    private final ModelT mModel;

    /**
     * Construct presenter and bind model. This normally happens *once* in a "business process" or "flow" lifecycle.
     *
     * @param model - normally injected via the Module in the process of obtaining the presenter - mock for testng ;-)
     */
    public BaseMvpPresenter(@NonNull ModelT model) {
        mModel = model;
        model.setListener(this);
    }

    @Override
    public void bindMvpView(@Nullable ViewT view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void bindMvpNavigation(@Nullable NavigationT navigation) {
        mNavigation = new WeakReference<>(navigation);
    }

    /**
     * Called when a new instance of a model needs to be initialized. This happens exactly once in the SMVP lifecycle
     * at startup.
     */
    @Override
    public void initializeModel(ModelUpdater<ModelT> modelInitializer) {
        if (modelInitializer != null) {
            modelInitializer.updateModel(getModel());
        }
    }

    /**
     * Called when a SMVP instance resumes in case the model needs to be updated.
     * Will only be called for existing instances (initializeModel has previously been called).
     * Initialize and Update model are mutually exclusive, initialize will be called once (and at that time, update will
     * not be called). After that, update will be called, but initialize will never be called again.
     */
    @Override
    public void updateModel(ModelUpdater<ModelT> modelUpdater) {
        if (modelUpdater != null) {
            modelUpdater.updateModel(getModel());
        }
    }

    /**
     * Called when the ui needs to be updated by the presenter with state from the model. This is called by the SMVP framework.
     */
    @Override
    public final void refreshView() {
        ViewT view = getView();
        if (view != null) {
            refreshView(view);
        }
    }

    /**
     * Obtain the currently bound View (MVP, not Android View)
     */
    @Nullable
    protected ViewT getView() {
        return mView.get();
    }

    /**
     * Obtain the contract for inter-screen navigation
     */
    @Nullable
    protected NavigationT getNavigation() {
        return mNavigation.get();
    }

    /**
     * Obtain the Model (MVP)
     */
    @NonNull
    protected ModelT getModel() {
        return mModel;
    }

    /**
     * Update view with state from model. Called after view bind change (including first view bind). Will be called
     */
    protected abstract void refreshView(@NonNull ViewT view);

    /**
     * Called at startup of a new SMVP instance. Note that the ui will not be available during this call.
     * It is intended for one-off activities such as recording analytics events for the first time a feature is
     * entered.
     */
    @CallSuper
    @Override
    public void onInitialize() {
    }
}
