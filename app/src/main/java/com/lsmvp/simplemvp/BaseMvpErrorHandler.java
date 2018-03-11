/*
 *  (C) 2017. National Australia Bank [All rights reserved]. This product and related documentation are protected by
 *  copyright restricting its use, copying, distribution, and decompilation. No part of this product or related
 *  documentation may be reproduced in any form by any means without prior written authorization of National Australia
 *  Bank. Unless otherwise arranged, third parties may not have access to this product or related documents.
 *
 */

package com.lsmvp.simplemvp;

/**
 * @author chrisjames
 * @date 14 JUN 2017
 */

public class BaseMvpErrorHandler {
//        implements NabErrorHandler {
//    private Bus mBus;
//    private Executor mMainThreadExecutor;
//
//    @Inject
//    public BaseMvpErrorHandler(Bus bus, @Named(MibApplicationModule.MAIN_THREAD_EXECUTOR) Executor executor) {
//        mBus = bus;
//        mMainThreadExecutor = executor;
//    }
//
//    protected void postToBus(Object message) {
//        mMainThreadExecutor.execute(new Runnable1Param<Object>(message) {
//            @Override
//            public void run() {
//                mBus.post(getParam());
//            }
//        });
//    }
//
//
//    // Network errors
//    @Override
//    public void onTimeoutError(NabError error) {
//        postToBus(new NetworkTimeoutError());
//    }
//
//    // Generic errors
//    @Override
//    public void onGenericError(NabError error) {
//        postToBus(new GenericError());
//    }
//
//    // HTTP errors
//    @Override
//    public void onInternalServerError(NabError error) {
//        postToBus(new GenericError());
//    }
//
//    @Override
//    public void onInvalidUserError(NabError error) {
//        postToBus(new UnauthorisedError(error));
//    }
//
//    @Override
//    public void onUserUnauthorizedError(NabError error) {
//        postToBus(new ClientSessionTimeoutError(error));
//    }
//
//    @Override
//    public void onConcurrentUserError(NabError error) {
//        postToBus(new ConcurrentUserError());
//    }
//
//    // Conversion error
//    @Override
//    public void onConversionError(NabError error) {
//        postToBus(new GenericError());
//    }
//
//    // SSL error
//    @Override
//    public void onSSLError(NabError error) {
//        postToBus(new GenericError());
//    }
//
}
