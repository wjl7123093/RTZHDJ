package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.StudyCoursewareContract;
import com.mytv.rtzhdj.mvp.model.StudyCoursewareModel;


@Module
public class StudyCoursewareModule {
    private StudyCoursewareContract.View view;

    /**
     * 构建StudyCoursewareModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public StudyCoursewareModule(StudyCoursewareContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    StudyCoursewareContract.View provideStudyCoursewareView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    StudyCoursewareContract.Model provideStudyCoursewareModel(StudyCoursewareModel model) {
        return model;
    }
}