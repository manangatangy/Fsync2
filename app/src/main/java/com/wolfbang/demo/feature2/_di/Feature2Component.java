package com.wolfbang.demo.feature2._di;

import com.wolfbang.demo.application.PerActivity;
import com.wolfbang.demo.application._di.MyApplicationComponent;
import com.wolfbang.demo.feature2.Feature2Contract.Presenter;
import com.wolfbang.demo.feature2.impl.Feature2Activity;

import dagger.Component;

/**
 * @author david
 * @date 09 Mar 2018.
 */

@PerActivity
@Component(
        dependencies = MyApplicationComponent.class,
        modules={Feature2Module.class}
)
public interface Feature2Component {

    Presenter providePresenter();
    void inject(Feature2Activity feature2Activity);

}
