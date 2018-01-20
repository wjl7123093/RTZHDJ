package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.StudyRecordContract;
import com.mytv.rtzhdj.mvp.model.StudyRecordModel;


@Module
public class StudyRecordModule {
    private StudyRecordContract.View view;

    /**
     * 构建StudyRecordModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public StudyRecordModule(StudyRecordContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    StudyRecordContract.View provideStudyRecordView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    StudyRecordContract.Model provideStudyRecordModel(StudyRecordModel model) {
        return model;
    }
}