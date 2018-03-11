package com.lsmvp.simplemvp;

import android.support.annotation.StringRes;

/**
 * @author chrisjames
 * @date 14 JUN 2017
 */

public interface ProgressDialogProvider {

    /**
     * show simple progress dialog
     */
    void showProgressDialog();

    /**
     * show progress dialog with formatted message
     */
    void showProgressDialog(@StringRes int messageTemplateId, Object... formatArgs);

    /**
     * hide progress dialog
     */
    void hideProgressDialog();

    /**
     * is Progress dialog Visible
     */
    boolean isProgressVisible();

}
