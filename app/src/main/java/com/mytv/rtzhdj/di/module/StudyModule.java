package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.StudyContract;
import com.mytv.rtzhdj.mvp.model.StudyModel;


@Module
public class StudyModule {
    private StudyContract.View view;

    /**
     * 构建StudyModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public StudyModule(StudyContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    StudyContract.View provideStudyView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    StudyContract.Model provideStudyModel(StudyModel model) {
        return model;
    }
}