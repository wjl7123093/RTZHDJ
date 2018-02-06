package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.NewsCommonContract;
import com.mytv.rtzhdj.mvp.model.NewsCommonModel;


@Module
public class NewsCommonModule {
    private NewsCommonContract.View view;

    /**
     * 构建NewsCommonModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NewsCommonModule(NewsCommonContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewsCommonContract.View provideNewsCommonView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NewsCommonContract.Model provideNewsCommonModel(NewsCommonModel model) {
        return model;
    }
}