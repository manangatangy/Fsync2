/*
 *  (C) 2017. National Australia Bank [All rights reserved]. This product and related documentation are protected by
 *  copyright restricting its use, copying, distribution, and decompilation. No part of this product or related
 *  documentation may be reproduced in any form by any means without prior written authorization of National Australia
 *  Bank. Unless otherwise arranged, third parties may not have access to this product or related documents.
 *
 */

package com.lsmvp.simplemvp;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * @author chrisjames
 * @date 14 JUN 2017
 * Executor that unconditionally posts to the main looper (via handler)
 * The default DI configuration will inject a (singleton) main looper handler
 *
 */
public class PostOnMainHandlerExecutor
        implements Executor {
    private final Handler mHandler;

    @Inject
    public PostOnMainHandlerExecutor(Handler handler) {
        mHandler = handler;
    }
    @Override
    public void execute(@NonNull Runnable command) {
        mHandler.post(command);
    }
}
