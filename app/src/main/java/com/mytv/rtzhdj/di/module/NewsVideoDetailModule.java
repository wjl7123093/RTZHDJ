package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.NewsVideoDetailContract;
import com.mytv.rtzhdj.mvp.model.NewsVideoDetailModel;


@Module
public class NewsVideoDetailModule {
    private NewsVideoDetailContract.View view;

    /**
     * 构建NewsVideoDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NewsVideoDetailModule(NewsVideoDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewsVideoDetailContract.View provideNewsVideoDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NewsVideoDetailContract.Model provideNewsVideoDetailModel(NewsVideoDetailModel model) {
        return model;
    }
}