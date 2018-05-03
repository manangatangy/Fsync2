package com.wolfbang.fsync.missionedit._di;

import com.wolfbang.fsync.missionedit.MissionEditContract.Presenter;
import com.wolfbang.fsync.missionedit.MissionEditContract.Model;

import com.wolfbang.fsync.missionedit.impl.MissionEditPresenter;
import com.wolfbang.fsync.missionedit.impl.MissionEditModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author david
 * @date 2018-05-03
 */
@Module
public class MissionEditModule {
    @Provides
    public Presenter providePresenter(Model model) {
        return new MissionEditPresenter(model);
    }

    // Binds a concrete implementation to provision of a model (decouples from provide presenter)
    @Provides
    Model provideModel(MissionEditModel model) {
        return model;
    }
}
