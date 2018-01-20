package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.NewsLiveContract;
import com.mytv.rtzhdj.mvp.model.NewsLiveModel;


@Module
public class NewsLiveModule {
    private NewsLiveContract.View view;

    /**
     * 构建NewsLiveModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NewsLiveModule(NewsLiveContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewsLiveContract.View provideNewsLiveView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NewsLiveContract.Model provideNewsLiveModel(NewsLiveModel model) {
        return model;
    }
}