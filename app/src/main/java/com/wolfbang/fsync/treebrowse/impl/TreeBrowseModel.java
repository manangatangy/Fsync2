package com.wolfbang.fsync.treebrowse.impl;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Model;

import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * @author david
 * @date 19 Mar 2018.
 */


public class TreeBrowseModel
        extends BaseMvpModel
        implements Model
{

    @Inject
    public TreeBrowseModel(Executor executor) {
        super(executor);
    }

}
