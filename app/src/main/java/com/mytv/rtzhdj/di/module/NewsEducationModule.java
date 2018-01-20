package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.NewsEducationContract;
import com.mytv.rtzhdj.mvp.model.NewsEducationModel;


@Module
public class NewsEducationModule {
    private NewsEducationContract.View view;

    /**
     * 构建NewsEducationModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NewsEducationModule(NewsEducationContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewsEducationContract.View provideNewsEducationView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NewsEducationContract.Model provideNewsEducationModel(NewsEducationModel model) {
        return model;
    }
}