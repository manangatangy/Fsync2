package com.wolfbang.fsync.feature2._di;

import com.wolfbang.fsync.application.PerActivity;
import com.wolfbang.fsync.application._di.FsyncApplicationComponent;
import com.wolfbang.fsync.feature2.Feature2Contract.Presenter;
import com.wolfbang.fsync.feature2.impl.Feature2Activity;

import dagger.Component;

/**
 * @author david
 * @date 09 Mar 2018.
 */

@PerActivity
@Component(
        dependencies = FsyncApplicationComponent.class,
        modules={Feature2Module.class}
)
public interface Feature2Component {

    Presenter providePresenter();
    void inject(Feature2Activity feature2Activity);

}
