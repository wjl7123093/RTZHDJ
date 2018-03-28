package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.VolunteerServiceDetailContract;
import com.mytv.rtzhdj.mvp.model.VolunteerServiceDetailModel;


@Module
public class VolunteerServiceDetailModule {
    private VolunteerServiceDetailContract.View view;

    /**
     * 构建VolunteerServiceDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public VolunteerServiceDetailModule(VolunteerServiceDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    VolunteerServiceDetailContract.View provideVolunteerServiceDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    VolunteerServiceDetailContract.Model provideVolunteerServiceDetailModel(VolunteerServiceDetailModel model) {
        return model;
    }
}