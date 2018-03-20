package com.wolfbang.fsync.treebrowse._di;

import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Model;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Presenter;
import com.wolfbang.fsync.treebrowse.impl.TreeBrowseModel;
import com.wolfbang.fsync.treebrowse.impl.TreeBrowsePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author david
 * @date 19 Mar 2018.
 */

@Module
public class TreeBrowseModule {

    @Provides
    public Presenter providePresenter(Model model) {
        return new TreeBrowsePresenter(model);
    }

    // Binds a concrete implementation to provision of a model (decouples from provide presenter)
    @Provides
    public Model provideModel(TreeBrowseModel model) {
        return model;
    }

}
