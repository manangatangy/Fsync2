package com.wolfbang.fsync.missionsummary._di;

import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Presenter;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Model;
import com.wolfbang.fsync.missionsummary.impl.MissionSummaryModel;
import com.wolfbang.fsync.missionsummary.impl.MissionSummaryPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author david
 * @date 09 Mar 2018.
 */

@Module
public class MissionSummaryModule {

    @Provides
    public Presenter providePresenter(Model model) {
        return new MissionSummaryPresenter(model);
    }

    // Binds a concrete implementation to provision of a model (decouples from provide presenter)
    @Provides
    public Model provideModel(MissionSummaryModel model) {
        return model;
    }

}
