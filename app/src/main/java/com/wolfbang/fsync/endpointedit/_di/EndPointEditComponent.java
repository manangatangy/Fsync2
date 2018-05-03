package com.wolfbang.fsync.endpointedit._di;

import com.wolfbang.fsync.application._di.FsyncApplicationComponent;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.Presenter;
import com.wolfbang.fsync.endpointedit.impl.EndPointEditFragment;
import com.wolfbang.shared.PerActivity;

import dagger.Component;

/**
 * @author david
 * @date 2018-05-03
 */
@PerActivity
@Component(
        dependencies = FsyncApplicationComponent.class,
        modules={EndPointEditModule.class}
)
public interface EndPointEditComponent {

    Presenter providePresenter();
    void inject(EndPointEditFragment fragment);

}

