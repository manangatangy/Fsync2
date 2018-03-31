package com.wolfbang.fsync.treebrowse.impl;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.BaseMvpPresenter;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Model;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.ModelListener;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Navigation;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Presenter;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.View;

/**
 * @author david
 * @date 19 Mar 2018.
 */


public class TreeBrowsePresenter
        extends BaseMvpPresenter<View, Model, Navigation>
        implements Presenter,
                   ModelListener {

    public TreeBrowsePresenter(@NonNull Model model) {
        super(model);
        model.setListener(this);
    }

    //region SimpleMVP
    @Override
    protected void refreshView(@NonNull View view) {
        view.setDirNode(getModel().getDirNode());
    }
    //endregion

    //region TreeBrowseContract.Presenter
//    @Override
//    public DirNode getDirNode() {
//        return getModel().getDirNode();
//    }

    @Override
    public void onBackClicked() {
        Navigation navigation = getNavigation();
        if (navigation != null) {
            navigation.navigateBack();
        }
    }

    @Override
    public void onItemClicked(FileNode fileNode) {
        if (fileNode instanceof DirNode) {
            DirNode dirNode = (DirNode)fileNode;
            Navigation navigation = getNavigation();
            if (navigation != null) {
                navigation.navigateToBrowseTree(dirNode);
            }
        }
    }
    //endregion

    //region TreeBrowseContract.ModelListener
    //endregion

}
