package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.mytv.rtzhdj.mvp.contract.NewsEducationSubContract;
import com.mytv.rtzhdj.mvp.model.NewsEducationSubModel;

import dagger.Module;
import dagger.Provides;


@Module
public class NewsEducationSubModule {
    private NewsEducationSubContract.View view;

    /**
     * 构建NewsEducationSubModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NewsEducationSubModule(NewsEducationSubContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewsEducationSubContract.View provideNewsEducationSubView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NewsEducationSubContract.Model provideNewsEducationSubModel(NewsEducationSubModel model) {
        return model;
    }
}