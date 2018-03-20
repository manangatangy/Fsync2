package com.wolfbang.fsync.treebrowse.impl;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
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

    private DirNode dirNode;

    @Inject
    public TreeBrowseModel(Executor executor) {
        super(executor);
    }

    @Override
    public void setDirNode(DirNode dirNode) {
        this.dirNode = dirNode;
    }

    @Override
    public DirNode getDirNode() {
        return dirNode;
    }

}
