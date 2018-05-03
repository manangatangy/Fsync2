package com.wolfbang.fsync.missionedit._di;

import com.wolfbang.fsync.application._di.FsyncApplicationComponent;
import com.wolfbang.fsync.missionedit.MissionEditContract.Presenter;
import com.wolfbang.fsync.missionedit.impl.MissionEditFragment;
import com.wolfbang.shared.PerActivity;

import dagger.Component;

/**
 * @author david
 * @date 2018-05-03
 */
@PerActivity
@Component(
        dependencies = FsyncApplicationComponent.class,
        modules={MissionEditModule.class}
)
public interface MissionEditComponent {

    Presenter providePresenter();
    void inject(MissionEditFragment missionEditFragment);

}

