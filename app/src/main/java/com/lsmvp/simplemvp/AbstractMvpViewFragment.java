/*
 *  (C) 2017. National Australia Bank [All rights reserved]. This product and related documentation are protected by
 *  copyright restricting its use, copying, distribution, and decompilation. No part of this product or related
 *  documentation may be reproduced in any form by any means without prior written authorization of National Australia
 *  Bank. Unless otherwise arranged, third parties may not have access to this product or related documents.
 *
 */

package com.lsmvp.simplemvp;

/**
 * Created by chrisjames on 2/3/17.
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;

import com.lsmvp.keyboard.KeyboardUtils;
import com.lsmvp.simplemvp.AbstractMvpContract.AbstractModel;
import com.lsmvp.simplemvp.AbstractMvpContract.AbstractPresenter;
import com.lsmvp.simplemvp.AbstractMvpContract.AbstractView;
import com.lsmvp.application.BaseApplicationModule;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.Lazy;
import timber.log.Timber;

//import au.com.nab.mobile.util.DialogFactory;
//import au.com.nab.mobile.view.widget.CustomAlertDialogBuilder;
//import au.com.nab.nabcommonui.util.KeyboardUtils;

//import com.squareup.otto.Bus;
//import com.squareup.otto.Subscribe;
//import au.com.nab.mobile.R;
//import au.com.nab.mobile.Whiteboard;
//import au.com.nab.mobile.accessibility.Announce;
//import au.com.nab.mobile.analytics.Analytics;
//import au.com.nab.mobile.error.NetworkTimeoutError;
//import au.com.nab.mobile.framework.modules.MibApplicationModule;
//import au.com.nab.mobile.presenter.state.authentication.BaseAuthenticationState;
//import au.com.nab.mobile.shared.ErrorDialogDismissListener;
//import au.com.nab.mobile.shared.NestedViewHost;
//import au.com.nab.mobile.shared.ObjectRegistry;
//import au.com.nab.mobile.shared.ProgressDialogProvider;
//import au.com.nab.mobile.shared.Runnable1Param;
//import au.com.nab.mobile.shared.Runnable3Param;
//import au.com.nab.mobile.shared.Runnable5Param;
//import au.com.nab.mobile.shared.SingleTopAwareFragment;


/**
 * @author Chris James
 * @date 2 Mar 2017 Base Simple MVP Fragment implementation. Does not include any dialog, error, keyboard or timeout
 * handling. These should be composed-in or inherited later in the hierarchy. The AbstractMvpXXX classes are purely to
 * introduce and support the architecture and lifecycle.
 */

public abstract class AbstractMvpViewFragment<PresenterT extends AbstractPresenter, ModelT extends AbstractModel,
        ComponentT>
        extends Fragment
        implements AbstractView,
                   AbstractMvpContract.BasicNavigation,
                   NestedViewHost,
                   SingleTopAwareFragment,
                   ProgressDialogProvider {

    // region Private Fields

    private static final String STATE_PRESENTER = "presenter";


    private PresenterT mPresenter;
    private ComponentT mComponent;

    private Unbinder mUnbinder;


//    private NetworkTimeoutHandler mNetworkTimeoutHandler = new NetworkTimeoutHandler();

    private AlertDialog mAlertDialog; // TODO: Kill

    private Intent mNewIntent;
    private boolean mJustLaunched = true;

    // endregion

    // region Injected fields
    // - please don't use these directly, use the getter methods - some of these fields may change
    // - As many of these are lazy as possible to reduce startup time

    @Inject
    @Named(BaseApplicationModule.MAIN_THREAD_EXECUTOR)
    protected Lazy<Executor> mLazyMainThreadExecutor;

    @Inject
    protected Lazy<ObjectRegistry> mLazyObjectRegistry;

//    @Inject
//    protected Lazy<Announce> mLazyAnnounce;
//
//    @Inject
//    protected Lazy<Bus> mBus;
//
//    @Inject
//    protected Lazy<Whiteboard> mWhiteboard;
//
//    @Inject
//    protected Lazy<BaseAuthenticationState> mAuthenticatedState;
//
//    @Inject
//    protected Lazy<Analytics> mAnalytics;

    // endregion

    // region Android lifecycle events

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

            onReconnectedInstance();
        }
//        getAuthenticatedState().registerBus();
//        getBus().register(mNetworkTimeoutHandler);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Put our presenter into the (lazy injected application scope singleton) object registry
        String objectRegistryKey = getObjectRegistry().put(mPresenter);

        // Save the object registry key for our presenter in our instance state bundle
        outState.putString(STATE_PRESENTER, objectRegistryKey);
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

    /**
     * Made final here to discourage life cycle abuse. If you feel you need to override this it likely means you need to
     * override a later lifecycle event or investigate your particular use case more thoroughly. Use onBound if you need
     * to attach listeners.. do not set initial values for the views
     */
    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater,
                                   @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        int layoutResource = getLayoutResource();
        if (layoutResource > 0) {
            View view = inflater.inflate(layoutResource, container, false);
            onBindViews(view);
            onBound();
            return view;
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        getAuthenticatedState().unRegisterBus();
//        getBus().unregister(mNetworkTimeoutHandler);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (mUnbinder != null) {
                mUnbinder.unbind();
            }
        } catch (Exception e) {
            Timber.w("Non fatal unbinding exception" + e.getMessage());
        }
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

//        getAuthenticatedState().resume();

        if (mPresenter != null) {
            // Ask presenter to
            mPresenter.updateModel(getModelUpdater());
            // Ask the presenter to apply any required state
            mPresenter.refreshView();
        }

        if (mJustLaunched) {
            mJustLaunched = false;
            if (mNewIntent != null) {
                onIntent(mNewIntent, true);
                mNewIntent = null;
            } else {
                onIntent(getActivity().getIntent(), false);
            }
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

    // region intent handling for bundle args

    /**
     * Override to handle the startup intent parameters.
     * The intent can be passed either from hosting activity startup - general case
     * or by intent update - clear-top/single-top case.
     * This is the single place to handle the intent.
     * no need to call on to super. Default implementation does nothing.
     *
     * This callback will be called after onResume has finished binding the view and presenter.
     * This callback will be called when the fragment is created from anew or being relaunched for a new intent only,
     * it won't be called for any other scenario of resumption.
     */
    public void onIntent(@Nullable Intent intent, boolean isOnNew) {
        // Handle the intent here
    }

    /**
     * This will be automatically called when the hosting activity has been popped to the top of the stack via
     * clear-top/single-top intent.
     */
    @Override
    public final void onNewIntent(Intent intent) {
        mNewIntent = intent;
        mJustLaunched = true;
    }

    // endregion

    // region MVPHost implementation

    /**
     * Get this fragment's parent "NestedViewHost" (abstract concept, may be fragment or activity).
     */
    @Override
    public NestedViewHost getHostParent() {
        Object parent = getParentFragment();
        if (parent == null) {
            parent = getActivity();
        }
        if (parent instanceof NestedViewHost) {
            return (NestedViewHost) parent;
        } else {
            return null;
        }
    }

    // endregion

    // region BasicNavigation implementation
    @Override
    public void navigateExit() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            getPostOnMainHandlerExecutor().execute(
                    new Runnable1Param<Activity>(activity) {
                        @Override
                        public void run() {
                            getParam().finish();
                        }
                    }
            );
        }
    }

    // endregion

    // region Helper methods and getters

    /**
     * Search up through the nested view hosts (fragment/activity heirachy) until a class compatible with ClassT is
     * found or null if none found..
     * eg ProgressDialogProvider progressProvider = findHostImplementing(ProgressDialogProvider.class);
     *
     * @return First instance of a class compatible with the supplied class.
     */
    @Nullable
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

    /**
     * Obtain the object registry
     */
    protected ObjectRegistry getObjectRegistry() {
        return mLazyObjectRegistry.get();
    }

    /**
     * Obtain the announcer
     */
//    protected Announce getAnnounce() {
//        return mLazyAnnounce.get();
//    }
//
//    /**
//     * Obtain analytics
//     */
//    protected Analytics getAnalytics() {
//        return mAnalytics.get();
//    }


    /**
     * Execute runnable on UI thread. Posts to UI handler if not currently on UI thread or calls run. Re-implemented
     * here instead of delegated to containing activity as the containing activity may not be available in some (very
     * rare) instances.
     */
    protected void runOnUiThread(@NonNull Runnable runnable) {
        // Test if we are on the UI Thread
        if (Looper.myLooper() != Looper.getMainLooper()) {
            // No, post to our handler (which is on the UI Thread)

            getPostOnMainHandlerExecutor().execute(runnable);

        } else {
            runnable.run();
        }
    }


    /**
     * Obtain this Fragment's Dagger Component. Shouldn't return null in most cases. If you are getting null, you are
     * attempting to use it too early in the fragment lifecycle.
     */
    @Nullable
    protected ComponentT getComponent() {
        return mComponent;
    }

    /**
     * Obtain this Fragment's Presenter (MVP).
     */
    @NonNull
    protected PresenterT getPresenter() {
        return mPresenter;
    }


    protected void showSoftKeyboard(View view) {
        KeyboardUtils.showKeyboard(view);
    }

    // endregion

    // region horror story dialogs

//    public void showError() {
//        showError(R.string.me0005_title, R.string.me0005);
//    }
//
//    public void showError(int titleId, int messageId) {
//        showError(titleId, messageId, null);
//    }
//
//    public void showError(int titleId, int messageId, String dialogTag) {
//        runOnUiThread(new Runnable3Param<Integer, Integer, String>(titleId, messageId, dialogTag) {
//            @Override
//            public void run() {
//                showDialog(DialogFactory.createErrorDialogBuilder(getActivity(), getParam(), getParam2()), getParam3());
//            }
//        });
//    }

//    public void showErrorWithAccessibility(int titleId, final int titleAccessibilityId,
//                                           int messageId, int messageAccessibilityId,
//                                           String dialogTag) {
//        runOnUiThread(new Runnable5Param<Integer, Integer, Integer, Integer, String>(
//                titleId, titleAccessibilityId, messageId, messageAccessibilityId, dialogTag) {
//            @Override
//            public void run() {
//                CustomAlertDialogBuilder builder = DialogFactory.createErrorDialogBuilder(
//                        getActivity(), getParam(), getParam3());
//                builder.setTitleContentDescription(getString(getParam2()));
//                builder.setMessageContentDescription(getString(getParam4()));
//                showDialog(builder, getParam5());
//            }
//        });
//    }


//    protected void showDialog(CustomAlertDialogBuilder alertDialogBuilder, String dialogTag) {
//        dismissDialogIfShowing();
//        alertDialogBuilder.setOnDismissListener(new ErrorDismissListener(getPresenter(), dialogTag));
//        mAlertDialog = alertDialogBuilder.show();
//    }

    protected void dismissDialogIfShowing() {
//        mAlertDialog = DialogFactory.dismissDialogIfShowing(mAlertDialog);
    }

    // endregion


    // region Progress dialog provider

    @Override
    public void showProgressDialog() {

        ProgressDialogProvider progressDialogProvider = findHostImplementing(ProgressDialogProvider.class);

        if (progressDialogProvider != null && !isProgressVisible()) {
            runOnUiThread(new Runnable1Param<ProgressDialogProvider>(progressDialogProvider) {
                @Override
                public void run() {
                    getParam().showProgressDialog();
                }
            });
        }
    }

    @Override
    public void showProgressDialog(final @StringRes int messageTemplateId, final Object... formatArgs) {
        // Progress spinner code needs to be fully enclosed inside runOnUiThread in case we show progress then
        // hide progress in quick succession eg. due to network error. Without this, we can end up with an
        // infinite spinner scenario with error dialog underneath spinner so app becomes unusable
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ProgressDialogProvider progressDialogProvider = findHostImplementing(ProgressDialogProvider.class);

                if (progressDialogProvider != null && !isProgressVisible()) {
                    progressDialogProvider.showProgressDialog(messageTemplateId, formatArgs);
                }
            }
        });
    }

//    @Override
//    public void showProgressDialogWithAccessibility(final @StringRes int resId,
//                                                    final @StringRes int resAccessibilityId,
//                                                    final Object... formatArgs) {
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                ProgressDialogProvider progressDialogProvider = findHostImplementing(ProgressDialogProvider.class);
//
//                if (progressDialogProvider != null && !isProgressVisible()) {
//                    progressDialogProvider.showProgressDialogWithAccessibility(resId, resAccessibilityId, formatArgs);
//                }
//            }
//        });
//    }

    @Override
    public void hideProgressDialog() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ProgressDialogProvider progressDialogProvider = findHostImplementing(ProgressDialogProvider.class);

                if (progressDialogProvider != null && isProgressVisible()) {
                    progressDialogProvider.hideProgressDialog();
                }
            }
        });
    }

    @Override
    public boolean isProgressVisible() {
        ProgressDialogProvider progressDialogProvider = findHostImplementing(ProgressDialogProvider.class);
        if (progressDialogProvider != null) {
            return progressDialogProvider.isProgressVisible();
        } else {
            return false;
        }
    }

    // endregion

    // region Optional MVP lifecycle events

    @CallSuper
    protected void onNewInstance() {
    }

    @CallSuper
    protected void onReconnectedInstance() {
    }

    /**
     * Bind views in layout to fields. This implementation usses butterknife.. it would be better to make this abstract
     * and implement in an inherited "ButterKnifeMvpFragment" - but not really an issue here.
     */
    @CallSuper
    protected void onBindViews(View layoutRoot) {
        mUnbinder = ButterKnife.bind(this, layoutRoot);
    }

    /**
     * Perform any post-view binding actions. Do not set view default values here. The default values should be in
     * the Model (set during construction/injection) and will be applied to the views in the presenter redraw. You
     * might want to do things like constructing and setting adapter view adapters here.
     */
    @CallSuper
    protected void onBound() {
    }

    /**
     * Provide a model updater to be called *ONCE* only on new instances after injection but before first use.
     * This should be used to set model initial state where some of the state resides in Android framework entities
     * (eg intents and fragment arguments)
     */
    @Nullable
    protected ModelUpdater<ModelT> getModelInitializer() {
        return null;
    }

    /**
     * Provide a model updater to be called each time the fragment is resumed.
     * This should be used to update the model state when something may change during fragment backgrounding.
     * I don't expect this to be used much.
     */
    @Nullable
    protected ModelUpdater<ModelT> getModelUpdater() {
        return null;
    }

    // endregion

    // region Members that implementations must provide

    /**
     * Build the Dagger dependency graph for this fragment. Pass any initialising values or defaults to the model
     * as Module construction arguments here, also any primitive android resources (strings, formats, resource ids)
     * that may be needed. You should never need complex android resources in your presenter or model (eg bitmaps).
     * Only the information required for the View to resolve them.
     */
    @NonNull
    abstract protected ComponentT createComponent();

    /**
     * Call your injection method on the graph you created
     */
    protected abstract void doInjection(@NonNull ComponentT component);

    /**
     * Obtain the presenter. DO NOT INJECT THE Presenter. Call component.providePresenter() or whatever your presenter
     * provider is called. This is because we only want to create it once then re-use the instance to preserve state.
     */
    @NonNull
    protected abstract PresenterT createPresenter(@NonNull ComponentT component);

    /**
     * Returns the layout resource to be inflated as the View for the Fragment.
     */
    @LayoutRes
    protected abstract int getLayoutResource();

    //endregion

    // region private methods

    /**
     * Obtain the main thread executor (*always* posts to main looper)
     */
    private Executor getPostOnMainHandlerExecutor() {
        return mLazyMainThreadExecutor.get();
    }

    /**
     * Obtain the bus
     */
//    private Bus getBus() {
//        return mBus.get();
//    }
//
//    /**
//     * Obtain the current authentication state
//     */
//    private BaseAuthenticationState getAuthenticatedState() {
//        return mAuthenticatedState.get();
//    }

    // endregion

    // region MVP View common implementation

    @Override
    public void hideSoftKeyboard() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                KeyboardUtils.dismissKeyboard(getActivity());
            }
        });
    }

    // endregion

    // region private classes

    class ErrorDismissListener
            implements OnDismissListener {
        private final PresenterT mPresenter;
        private final String mDialogTag;

        ErrorDismissListener(PresenterT presenter, String dialogTag) {
            mPresenter = presenter;
            mDialogTag = dialogTag;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            mAlertDialog = null;
//            if (mPresenter instanceof ErrorDialogDismissListener) {
//                ((ErrorDialogDismissListener) mPresenter).onErrorDialogDismissed(mDialogTag);
//            }
        }
    }

//    class NetworkTimeoutHandler {
//        @Subscribe
//        public void onNetworkTimeoutError(NetworkTimeoutError error) {
//            hideProgressDialog();
//            showError(R.string.me0018_title, R.string.me0018);
//        }
//
//    }
    // endregion
}