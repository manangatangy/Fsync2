package com.lsmvp.simplemvp;

/**
 * Interface for a multi-page wizard activity consisting of multiple fragments requiring bi-directional fragment / page
 * navigation.
 *
 * @author Gary Chang
 * @date 5/04/2017
 */
public interface WizardFragmentListener {

    void onPrevPage();

    void onNextPage();

    void onComplete();

    void onCancel();
}
