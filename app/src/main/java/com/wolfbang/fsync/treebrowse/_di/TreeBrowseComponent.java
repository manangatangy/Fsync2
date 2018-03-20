package com.wolfbang.fsync.treebrowse._di;

import com.wolfbang.fsync.application._di.FsyncApplicationComponent;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Presenter;
import com.wolfbang.fsync.treebrowse.impl.TreeBrowseFragment;
import com.wolfbang.shared.PerActivity;

import dagger.Component;

/**
 * @author david
 * @date 19 Mar 2018.
 */

@PerActivity
@Component(
        dependencies = FsyncApplicationComponent.class,
        modules={TreeBrowseModule.class}
)
public interface TreeBrowseComponent {

    Presenter providePresenter();
    void inject(TreeBrowseFragment treeBrowseFragment);

}
