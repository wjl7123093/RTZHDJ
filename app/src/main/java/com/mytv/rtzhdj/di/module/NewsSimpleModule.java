package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.NewsSimpleContract;
import com.mytv.rtzhdj.mvp.model.NewsSimpleModel;


@Module
public class NewsSimpleModule {
    private NewsSimpleContract.View view;

    /**
     * 构建NewsSimpleModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NewsSimpleModule(NewsSimpleContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewsSimpleContract.View provideNewsSimpleView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NewsSimpleContract.Model provideNewsSimpleModel(NewsSimpleModel model) {
        return model;
    }
}