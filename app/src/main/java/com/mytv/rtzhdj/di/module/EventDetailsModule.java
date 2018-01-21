package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.EventDetailsContract;
import com.mytv.rtzhdj.mvp.model.EventDetailsModel;


@Module
public class EventDetailsModule {
    private EventDetailsContract.View view;

    /**
     * 构建EventDetailsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EventDetailsModule(EventDetailsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EventDetailsContract.View provideEventDetailsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EventDetailsContract.Model provideEventDetailsModel(EventDetailsModel model) {
        return model;
    }
}