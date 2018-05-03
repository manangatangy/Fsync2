package com.wolfbang.fsync.emptyclasses._di;

import com.wolfbang.fsync.emptyclasses.EmptyClassesContract.Presenter;
import com.wolfbang.fsync.emptyclasses.EmptyClassesContract.Model;

import com.wolfbang.fsync.emptyclasses.impl.EmptyClassesPresenter;
import com.wolfbang.fsync.emptyclasses.impl.EmptyClassesModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author david
 * @date 2018-05-03
 */
@Module
public class EmptyClassesModule {
    @Provides
    public Presenter providePresenter(Model model) {
        return new EmptyClassesPresenter(model);
    }

    // Binds a concrete implementation to provision of a model (decouples from provide presenter)
    @Provides
    Model provideModel(EmptyClassesModel model) {
        return model;
    }
}
