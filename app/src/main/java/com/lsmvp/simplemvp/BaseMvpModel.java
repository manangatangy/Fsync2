/*
 *  (C) 2017. National Australia Bank [All rights reserved]. This product and related documentation are protected by
 *  copyright restricting its use, copying, distribution, and decompilation. No part of this product or related
 *  documentation may be reproduced in any form by any means without prior written authorization of National Australia
 *  Bank. Unless otherwise arranged, third parties may not have access to this product or related documents.
 *
 */

package com.lsmvp.simplemvp;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import com.lsmvp.simplemvp.AbstractMvpContract.AbstractModel;
import com.lsmvp.simplemvp.AbstractMvpContract.AbstractModelListener;

/**
 * @author Gary Chang
 * @date 3/05/2017
 */

public class BaseMvpModel
        implements AbstractModel {

    private Executor mExecutor;
//    private SessionManager mSessionManager;
//    private BaseMvpErrorHandler mBaseMvpErrorHandler;

    private WeakReference<? extends AbstractModelListener> mModelListener = new WeakReference<>(null);

    @Inject
    public BaseMvpModel(Executor executor
//            ,
//                        SessionManager sessionManager,
//                        BaseMvpErrorHandler baseMvpErrorHandler
    ) {
        mExecutor = executor;
//        mSessionManager = sessionManager;
//        mBaseMvpErrorHandler = baseMvpErrorHandler;
    }

    // region AbstractMVPModel implementation
    @Override
    public <ListenerT extends AbstractModelListener> void setListener(ListenerT listener) {
        mModelListener = new WeakReference<AbstractModelListener>(listener);
    }

    @Override
    public <ListenerT extends AbstractModelListener> ListenerT getListener() {
        return (ListenerT) mModelListener.get();
    }

//    @Override
//    public boolean isSessionValid() {
//        return mSessionManager != null && mSessionManager.hasValidSession();
//    }

    // endregion

    // region protected getter methods
    protected Executor getExecutor() {
        return mExecutor;
    }

    /**
     * Use with caution. Not convinced entire session manager should be exposed here
     */
//    protected SessionManager getSessionManager() {
//        return mSessionManager;
//    }
//
//    protected ErrorClassification getErrorClassification(NabError nabError) {
//        return NabErrorDispatcher.mapNabErrorToErrorClassification(nabError);
//    }
//
//    public void dispatchError(NabError error) {
//        NabErrorDispatcher.onNabError(error, mBaseMvpErrorHandler);
//    }
//
//    public boolean isAppWideError(NabError nabError) {
//        return getErrorClassification(nabError).appWideError;
//    }
//
//    public boolean isError(@NonNull NabError nabError) {
//        return nabError.getErrorType() != ErrorType.NONE;
//    }
//
//    public boolean isNotError(@NonNull NabError nabError) {
//        return !isError(nabError);
//    }
    // endregion
}
