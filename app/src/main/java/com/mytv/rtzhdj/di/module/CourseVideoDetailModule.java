package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.CourseVideoDetailContract;
import com.mytv.rtzhdj.mvp.model.CourseVideoDetailModel;


@Module
public class CourseVideoDetailModule {
    private CourseVideoDetailContract.View view;

    /**
     * 构建CourseVideoDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CourseVideoDetailModule(CourseVideoDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CourseVideoDetailContract.View provideCourseVideoDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CourseVideoDetailContract.Model provideCourseVideoDetailModel(CourseVideoDetailModel model) {
        return model;
    }
}