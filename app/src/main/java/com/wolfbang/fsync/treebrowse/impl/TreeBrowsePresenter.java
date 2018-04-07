package com.wolfbang.fsync.treebrowse.impl;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.BaseMvpPresenter;
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
        view.populatePathElements(getModel().getCurrentPathAsNameList());
        view.populateList(getModel().getCurrentDirChildren());
    }
    //endregion

    //region TreeBrowseContract.Presenter
    @Override
    public boolean onBackClicked() {
        if (getModel().moveCurrentDirToParent()) {
            View view = getView();
            if (view != null) {
                view.popPathElement();
                view.populateList(getModel().getCurrentDirChildren());
            }
        } else {
            Navigation navigation = getNavigation();
            if (navigation != null) {
                navigation.navigateBack();
            }
        }
        return true;
    }

    @Override
    public void onListItemClicked(FileNode fileNode) {
        if (getModel().moveCurrentDirToChild(fileNode.getName())) {
            View view = getView();
            if (view != null) {
                view.addPathElement(getModel().getCurrentDirName());
                view.populateList(getModel().getCurrentDirChildren());
            }
        }
    }

    @Override
    public void onPathElementClicked(int index) {
        getModel().moveCurrentDirToLevel(index);
        View view = getView();
        if (view != null) {
            refreshView(view);
        }
    }
    //endregion

    //region TreeBrowseContract.ModelListener
    //endregion

}
