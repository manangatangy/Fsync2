package com.wolfbang.fsync.missionsummary._di;

import com.wolfbang.fsync.application.PerActivity;
import com.wolfbang.fsync.application._di.FsyncApplicationComponent;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Presenter;
import com.wolfbang.fsync.missionsummary.impl.Feature1Activity;
import com.wolfbang.fsync.missionsummary.impl.MissionSummaryFragment;

import dagger.Component;

/**
 * @author david
 * @date 09 Mar 2018.
 */

@PerActivity
@Component(
        dependencies = FsyncApplicationComponent.class,
        modules={MissionSummaryModule.class}
)
public interface MissionSummaryComponent {

    Presenter providePresenter();
    void inject(Feature1Activity feature1Activity);
    void inject(MissionSummaryFragment missionSummaryFragment);

}
