package com.wolfbang.fsync.missionlist._di;


import com.wolfbang.fsync.missionlist.MissionListContract.Model;
import com.wolfbang.fsync.missionlist.MissionListContract.Presenter;
import com.wolfbang.fsync.missionlist.impl.MissionListModel;
import com.wolfbang.fsync.missionlist.impl.MissionListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author david
 * @date 2018-05-03
 */
@Module
public class MissionListModule {
    @Provides
    public Presenter providePresenter(Model model) {
        return new MissionListPresenter(model);
    }

    // Binds a concrete implementation to provision of a model (decouples from provide presenter)
    @Provides
    Model provideModel(MissionListModel model) {
        return model;
    }
}
