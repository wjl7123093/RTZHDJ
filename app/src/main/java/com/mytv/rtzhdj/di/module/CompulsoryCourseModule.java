package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.CompulsoryCourseContract;
import com.mytv.rtzhdj.mvp.model.CompulsoryCourseModel;


@Module
public class CompulsoryCourseModule {
    private CompulsoryCourseContract.View view;

    /**
     * 构建CompulsoryCourseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CompulsoryCourseModule(CompulsoryCourseContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CompulsoryCourseContract.View provideCompulsoryCourseView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CompulsoryCourseContract.Model provideCompulsoryCourseModel(CompulsoryCourseModel model) {
        return model;
    }
}