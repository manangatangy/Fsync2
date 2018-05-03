package com.wolfbang.fsync.endpointlist._di;

import com.wolfbang.fsync.application._di.FsyncApplicationComponent;
import com.wolfbang.fsync.endpointlist.EndPointListContract.Presenter;
import com.wolfbang.fsync.endpointlist.impl.EndPointListFragment;
import com.wolfbang.shared.PerActivity;

import dagger.Component;

/**
 * @author david
 * @date 2018-05-03
 */
@PerActivity
@Component(
        dependencies = FsyncApplicationComponent.class,
        modules={EndPointListModule.class}
)
public interface EndPointListComponent {

    Presenter providePresenter();
    void inject(EndPointListFragment fragment);

}

