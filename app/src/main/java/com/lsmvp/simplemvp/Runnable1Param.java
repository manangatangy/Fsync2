package com.lsmvp.simplemvp;

/**
 * @author Chris James
 * @date 10/03/2017
 */
public abstract class Runnable1Param<ParamT> implements Runnable {
    private final ParamT mParam;

    public Runnable1Param(ParamT param) {
        mParam = param;
    }

    protected ParamT getParam() {
        return mParam;
    }
}
