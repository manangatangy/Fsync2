package com.wolfbang.demo.feature2._di;

import com.wolfbang.demo.feature2.Feature2Contract.Presenter;
import com.wolfbang.demo.feature2.Feature2Contract.Model;
import com.wolfbang.demo.feature2.impl.Feature2Model;
import com.wolfbang.demo.feature2.impl.Feature2Presenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author david
 * @date 09 Mar 2018.
 */

@Module
public class Feature2Module {

    @Provides
    public Presenter providePresenter(Model model) {
        return new Feature2Presenter(model);
    }

    // Binds a concrete implementation to provision of a model (decouples from provide presenter)
    @Provides
    public Model provideModel(Feature2Model model) {
        return model;
    }

}
