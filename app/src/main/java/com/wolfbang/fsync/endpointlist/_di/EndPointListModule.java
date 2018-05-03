package com.wolfbang.fsync.endpointlist._di;

import com.wolfbang.fsync.endpointlist.EndPointListContract.Model;
import com.wolfbang.fsync.endpointlist.EndPointListContract.Presenter;
import com.wolfbang.fsync.endpointlist.impl.EndPointListModel;
import com.wolfbang.fsync.endpointlist.impl.EndPointListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author david
 * @date 2018-05-03
 */
@Module
public class EndPointListModule {
    @Provides
    public Presenter providePresenter(Model model) {
        return new EndPointListPresenter(model);
    }

    // Binds a concrete implementation to provision of a model (decouples from provide presenter)
    @Provides
    Model provideModel(EndPointListModel model) {
        return model;
    }
}
