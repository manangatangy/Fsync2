package com.wolfbang.fsync.endpointedit._di;


import com.wolfbang.fsync.endpointedit.EndPointEditContract.Model;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.Presenter;
import com.wolfbang.fsync.endpointedit.impl.EndPointEditModel;
import com.wolfbang.fsync.endpointedit.impl.EndPointEditPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author david
 * @date 2018-05-03
 */
@Module
public class EndPointEditModule {
    @Provides
    public Presenter providePresenter(Model model) {
        return new EndPointEditPresenter(model);
    }

    // Binds a concrete implementation to provision of a model (decouples from provide presenter)
    @Provides
    Model provideModel(EndPointEditModel model) {
        return model;
    }
}
