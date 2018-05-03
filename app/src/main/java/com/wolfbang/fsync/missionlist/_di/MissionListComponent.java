package com.wolfbang.fsync.missionlist._di;

import com.wolfbang.fsync.application._di.FsyncApplicationComponent;
import com.wolfbang.fsync.missionlist.MissionListContract.Presenter;
import com.wolfbang.fsync.missionlist.impl.MissionListFragment;
import com.wolfbang.shared.PerActivity;

import dagger.Component;

/**
 * @author david
 * @date 2018-05-03
 */
@PerActivity
@Component(
        dependencies = FsyncApplicationComponent.class,
        modules={MissionListModule.class}
)
public interface MissionListComponent {

    Presenter providePresenter();
    void inject(MissionListFragment fragment);

}

