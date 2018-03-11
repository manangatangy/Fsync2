package com.wolfbang.demo.feature1._di;

import com.wolfbang.demo.application.PerActivity;
import com.wolfbang.demo.feature1.Feature1Contract.Presenter;
import com.wolfbang.demo.feature1.Feature1Contract.Model;
import com.wolfbang.demo.feature1.impl.Feature1Model;
import com.wolfbang.demo.feature1.impl.Feature1Presenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author david
 * @date 09 Mar 2018.
 */

@Module
public class Feature1Module {

    @Provides
    Presenter providePresenter(Model model) {
        return new Feature1Presenter(model);
    }

    // Binds a concrete implementation to provision of a model (decouples from provide presenter)
    @Provides
    Model provideModel(Feature1Model model) {
        return model;
    }

}
