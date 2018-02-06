package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.NewsCleanContract;
import com.mytv.rtzhdj.mvp.model.NewsCleanModel;


@Module
public class NewsCleanModule {
    private NewsCleanContract.View view;

    /**
     * 构建NewsCleanModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NewsCleanModule(NewsCleanContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewsCleanContract.View provideNewsCleanView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NewsCleanContract.Model provideNewsCleanModel(NewsCleanModel model) {
        return model;
    }
}