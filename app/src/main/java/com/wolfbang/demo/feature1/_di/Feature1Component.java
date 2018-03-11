package com.wolfbang.demo.feature1._di;

import com.wolfbang.demo.application.PerActivity;
import com.wolfbang.demo.application._di.MyApplicationComponent;
import com.wolfbang.demo.feature1.Feature1Contract.Presenter;
import com.wolfbang.demo.feature1.impl.Feature1Activity;
import com.wolfbang.demo.feature1.impl.Feature1Fragment;

import dagger.Component;

/**
 * @author david
 * @date 09 Mar 2018.
 */

@PerActivity
@Component(
        dependencies = MyApplicationComponent.class,
        modules={Feature1Module.class}
)
public interface Feature1Component {

    Presenter providePresenter();
    void inject(Feature1Activity feature1Activity);
    void inject(Feature1Fragment feature1Fragment);

}
