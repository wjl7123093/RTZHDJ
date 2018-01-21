package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.SurroundingCommunityContract;
import com.mytv.rtzhdj.mvp.model.SurroundingCommunityModel;


@Module
public class SurroundingCommunityModule {
    private SurroundingCommunityContract.View view;

    /**
     * 构建SurroundingCommunityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SurroundingCommunityModule(SurroundingCommunityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SurroundingCommunityContract.View provideSurroundingCommunityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SurroundingCommunityContract.Model provideSurroundingCommunityModel(SurroundingCommunityModel model) {
        return model;
    }
}