package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.VolunteerServiceContract;
import com.mytv.rtzhdj.mvp.model.VolunteerServiceModel;


@Module
public class VolunteerServiceModule {
    private VolunteerServiceContract.View view;

    /**
     * 构建VolunteerServiceModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public VolunteerServiceModule(VolunteerServiceContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    VolunteerServiceContract.View provideVolunteerServiceView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    VolunteerServiceContract.Model provideVolunteerServiceModel(VolunteerServiceModel model) {
        return model;
    }
}