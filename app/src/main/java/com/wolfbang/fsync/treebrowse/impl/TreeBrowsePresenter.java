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
        DirNode dirNode = getModel().getCurrentDirNode();
        String[] pathNames = getModel().getParentNames();
        view.populatePathElements(pathNames);
        view.populateList(dirNode.toChildrenArray());
    }
    //endregion

    //region TreeBrowseContract.Presenter
//    @Override
//    public DirNode getDirNode() {
//        return getModel().getDirNode();
//    }

    @Override
    public boolean onBackClicked() {
        if (getModel().goToParent()) {
            DirNode dirNode = getModel().getCurrentDirNode();
            View view = getView();
            if (view != null) {
                view.popPathElement();
                view.populateList(dirNode.toChildrenArray());
            }
            return true;
        }
        return false;
        // pop ?

//        Navigation navigation = getNavigation();
//        if (navigation != null) {
//            navigation.navigateBack();
//        }
    }

    @Override
    public void onListItemClicked(FileNode fileNode) {
        if (getModel().goToChild(fileNode.getName())) {
            DirNode dirNode = getModel().getCurrentDirNode();
            View view = getView();
            if (view != null) {
                view.addPathElement(dirNode.getName());
                view.populateList(dirNode.toChildrenArray());
            }
        }
    }

    @Override
    public void onPathElementClicked(int index) {

    }
    //endregion

    //region TreeBrowseContract.ModelListener
    //endregion

}
