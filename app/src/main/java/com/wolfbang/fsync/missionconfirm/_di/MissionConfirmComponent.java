package com.wolfbang.fsync.missionconfirm._di;

import com.wolfbang.fsync.application._di.FsyncApplicationComponent;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Presenter;
import com.wolfbang.fsync.missionconfirm.impl.MissionConfirmFragment;
import com.wolfbang.shared.PerActivity;

import dagger.Component;

/**
 * @author david
 * @date 22 Mar 2018.
 */

@PerActivity
@Component(
        dependencies = FsyncApplicationComponent.class,
        modules={MissionConfirmModule.class}
)
public interface MissionConfirmComponent {

    Presenter providePresenter();
    void inject(MissionConfirmFragment missionConfirmFragment);

}
