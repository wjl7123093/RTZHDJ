package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.NewsAllContract;
import com.mytv.rtzhdj.mvp.model.NewsAllModel;


@Module
public class NewsAllModule {
    private NewsAllContract.View view;

    /**
     * 构建NewsAllModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NewsAllModule(NewsAllContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewsAllContract.View provideNewsAllView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NewsAllContract.Model provideNewsAllModel(NewsAllModel model) {
        return model;
    }
}