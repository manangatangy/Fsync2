/*
 *  (C) 2017. National Australia Bank [All rights reserved]. This product and related documentation are protected by
 *  copyright restricting its use, copying, distribution, and decompilation. No part of this product or related
 *  documentation may be reproduced in any form by any means without prior written authorization of National Australia
 *  Bank. Unless otherwise arranged, third parties may not have access to this product or related documents.
 *
 */

package com.lsmvp.simplemvp;

import android.content.Context;

import java.lang.ref.WeakReference;

import com.lsmvp.simplemvp.AbstractMvpContract.AbstractModel;
import com.lsmvp.simplemvp.AbstractMvpContract.AbstractPresenter;

/**
 * Wizard fragment aware MVP view fragment.
 *
 * @author Gary Chang
 * @date 5/04/2017
 */

public abstract class AbstractWizardMvpViewFragment
        <PresenterT extends AbstractPresenter,
                ModelT extends AbstractModel,
                ComponentT,
                ListenerT extends WizardFragmentListener>
        extends AbstractMvpViewFragment<PresenterT, ModelT, ComponentT> {

    protected WeakReference<WizardFragmentListener> mWizardFragmentListenerWeakReference = new WeakReference<>(null);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WizardFragmentListener) {
            mWizardFragmentListenerWeakReference = new WeakReference<>((WizardFragmentListener) context);
        }
    }

    public ListenerT getWizardFragmentListener() {
        return (ListenerT) mWizardFragmentListenerWeakReference.get();
    }
}
