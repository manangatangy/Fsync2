/*
 *  (C) 2017. National Australia Bank [All rights reserved]. This product and related documentation are protected by
 *  copyright restricting its use, copying, distribution, and decompilation. No part of this product or related
 *  documentation may be reproduced in any form by any means without prior written authorization of National Australia
 *  Bank. Unless otherwise arranged, third parties may not have access to this product or related documents.
 *
 */

package com.lsmvp.simplemvp;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.lsmvp.keyboard.KeyboardUtils;
import com.lsmvp.simplemvp.AbstractMvpContract.AbstractModel;
import com.lsmvp.simplemvp.AbstractMvpContract.AbstractPresenter;
import com.lsmvp.simplemvp.AbstractMvpContract.AbstractView;
import com.lsmvp.simplemvp.AbstractMvpContract.BasicNavigation;
import com.lsmvp.application.BaseApplication;
import butterknife.ButterKnife;

/**
 * @author Chris James
 * @date 10 Mar 2017 Base Simple MVP Activity implementation. Does not include any dialog, error, keyboard or timeout
 * handling. These should be composed-in or inherited later in the heirachy. The AbstractMvpXXX classes are purely to
 * introduce and support the architecture and lifecycle
 */
public abstract class AbstractMvpViewActivity<PresenterT extends AbstractPresenter, ModelT extends AbstractModel,
        ComponentT>
        extends AppCompatActivity
        implements AbstractView,
                   NestedViewHost,
                   BasicNavigation {

    // region Private Fields

    private static final String STATE_PRESENTER = "presenter";

    private PresenterT mPresenter;

    private ComponentT mComponent;

    // endregion

    //region Android lifecycle events

    /**
     * Made final here to discourage life cycle abuse. If you feel you need to override this it likely means you need to
     * override a later lifecycle event or investigate your particular use case more thoroughly
     */
    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Build injection graph
        mComponent = createComponent();

        // Single point of injection
        doInjection(mComponent);

        onPreContentConfigure();

        int layoutResource = getLayoutResource();
        setContentView(layoutResource);

        onBindViews();

        if (savedInstanceState == null) {
            // This is a new instance..
            createNewSMVPInstance();
        } else {
            // This isn't a new instance
            // retrieve our presenter from the object registry
            String registryKey = savedInstanceState.getString(STATE_PRESENTER);
            if (registryKey != null) {
                mPresenter = getObjectRegistry().get(registryKey);
            } else {
                // Should never happen, but handle it correctly anyway
                createNewSMVPInstance();
            }

            onPostBindRestoredInstance(savedInstanceState);
        }

        onBound();
    }

    /**
     * Construct a new SMVP instance (the stack below the ui)
     */
    private void createNewSMVPInstance() {
        // Obtain a new presenter from our injection graph
        mPresenter = createPresenter(mComponent);
        mPresenter.initializeModel(getModelInitializer());
        mPresenter.onInitialize();
        onNewInstance();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Put our presenter into the (lazy injected application scope singleton) object registry
        String objectRegistryKey = getObjectRegistry().put(mPresenter);

        // Save the object registry key for our presenter in our instance state bundle
        outState.putString(STATE_PRESENTER, objectRegistryKey);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            // Bind this view to the presenter
            mPresenter.bindMvpView(this);
            mPresenter.bindMvpNavigation(this);
        }
    }

    /**
     * Refresh is done in onResume instead of onStart as not all view state is unfrozen during onStart. This can result
     * in some views (TextView) having inconsistent content after refreshView is called.
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            // Ask the presenter to apply any required state
            mPresenter.refreshView();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Unbind the presenter from this view as this view may be inactive
        if (mPresenter != null) {
            mPresenter.bindMvpView(null);
            mPresenter.bindMvpNavigation(null);
        }
    }

    // endregion

    // region Helper and getter methods

    /**
     * Obtain the object registry from the Dagger object graph without requiring direct injection
     * in most cases activities won't need to inject components so this avoids the need to expect
     * a member injecton component in the graph
     */
    protected ObjectRegistry getObjectRegistry() {
        return BaseApplication.getBaseApplicationComponent().getObjectRegistry();
    }

    /**
     * Obtain this Activity's Presenter (MVP). Shouldn't return null in most cases. If you are getting null, you are
     * attempting to use it too early in the activity lifecycle.
     */
    @Nullable
    protected PresenterT getPresenter() {
        return mPresenter;
    }

    /**
     * Obtain this Activity's Dagger component. Shouldn't return null in most cases. If you are getting null, you are
     * attempting to use it too early in the activity lifecycle.
     */
    @Nullable
    protected ComponentT getComponent() {
        return mComponent;
    }

    /**
     * Really not convinced this should be here :-(
     */
    @Override
    public void hideProgressDialog() {
    }

    @Override
    public void showProgressDialog() {
    }

    @Override
    public void showProgressDialog(final @StringRes int messageTemplateId, final Object... formatArgs) {
    }

    @Override
    public boolean isProgressVisible() {

        return false;
    }

    @Override
    public void hideSoftKeyboard() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                KeyboardUtils.dismissKeyboard(AbstractMvpViewActivity.this);
            }
        });
    }
// endregion

    // region Optional MVP life cycle events

    /**
     * Some Activity attributes must be set before providing content (window features for example)
     * Do that here (if required).
     */
    @CallSuper
    protected void onPreContentConfigure() {
    }

    /**
     * Bind views in layout to fields. This implementation usses butterknife.. it would be better to make this abstract
     * and implement in an inherited "ButterKnifeMvpFragment" - but not really an issue here.
     */
    @CallSuper
    protected void onBindViews() {
        ButterKnife.bind(this);
    }

    /**
     * Perform any post-view binding actions. Do not set view default values here. The default values should be in
     * the Model (set during construction/injection) and will be applied to the views in the presenter redraw. You
     * might want to do things like constructing and setting adapter view adapters here.
     */
    @CallSuper
    protected void onBound() {
    }


    @CallSuper
    protected void onNewInstance() {
    }

    @CallSuper
    protected void onPostBindRestoredInstance(Bundle savedInstance) {
    }

    /**
     * Call your injection method on the graph you created
     */
    @CallSuper
    protected void doInjection(@NonNull ComponentT component) {
    }


    // endregion


    // region NestedViewHost implementation

    @Override
    public NestedViewHost getHostParent() {
        // Activities can't be nested inside anything else
        return null;
    }

    /**
     * Search up through the nested view hosts (fragment/activity heirachy) until a class compatible with ClassT is
     * found or null if none found..
     * eg ProgressDialogProvider progressProvider = findHostImplementing(ProgressDialogProvider.class);
     *
     * @return First instance of a class compatible with the supplied class.
     */
    protected <ClassT> ClassT findHostImplementing(Class<ClassT> clazz) {
        NestedViewHost parent = getHostParent();

        while (parent != null) {
            if (clazz.isAssignableFrom(parent.getClass())) {
                return (ClassT) parent;
            }

            parent = parent.getHostParent();
        }
        return null;
    }

    // endregion

    // region MvpHost implementation

    @Override
    public void navigateExit() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

    // endregion

    // region Members that implementations must provide

    /**
     * Build the Dagger dependency graph for this activity. Pass any initialising values or defaults to the model
     * as Module construction arguments here, also any primitive android resources (strings, formats, resource ids)
     * that may be needed. You should never need complex android resources in your presenter or model (eg bitmaps).
     * Only the information required for the View to resolve them.
     */
    @NonNull
    abstract protected ComponentT createComponent();


    /**
     * Obtain the presenter. DO NOT INJECT THE Presenter. Call component.providePresenter() or whatever your presenter
     * provider is called. This is because we only want to create it once then re-use the instance to preserve state.
     */
    @NonNull
    abstract protected PresenterT createPresenter(@NonNull ComponentT component);

    @Nullable
    abstract protected ModelUpdater<ModelT> getModelInitializer();


    /**
     * Returns the layout resource to be inflated as the View for the Activity.
     */
    @LayoutRes
    protected abstract int getLayoutResource();

    //endregion
}