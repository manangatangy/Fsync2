package com.wolfbang.fsync.emptyclasses._di;

import com.wolfbang.fsync.application._di.FsyncApplicationComponent;
import com.wolfbang.fsync.emptyclasses.EmptyClassesContract.Presenter;
import com.wolfbang.fsync.emptyclasses.impl.EmptyClassesFragment;
import com.wolfbang.shared.PerActivity;

import dagger.Component;

/**
 * @author david
 * @date 2018-05-03
 */
@PerActivity
@Component(
        dependencies = FsyncApplicationComponent.class,
        modules={EmptyClassesModule.class}
)
public interface EmptyClassesComponent {

    Presenter providePresenter();
    void inject(EmptyClassesFragment fragment);

}

