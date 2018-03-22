package com.wolfbang.fsync.missionconfirm._di;


import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Presenter;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Model;
import com.wolfbang.fsync.missionconfirm.impl.MissionConfirmModel;
import com.wolfbang.fsync.missionconfirm.impl.MissionConfirmPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author david
 * @date 22 Mar 2018.
 */

@Module
public class MissionConfirmModule {

    @Provides
    public Presenter providePresenter(Model model) {
        return new MissionConfirmPresenter(model);
    }

    // Binds a concrete implementation to provision of a model (decouples from provide presenter)
    @Provides
    public Model provideModel(MissionConfirmModel model) {
        return model;
    }

}
