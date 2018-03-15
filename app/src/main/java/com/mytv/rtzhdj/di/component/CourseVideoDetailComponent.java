package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.CourseVideoDetailModule;

import com.mytv.rtzhdj.mvp.ui.activity.CourseVideoDetailActivity;

@ActivityScope
@Component(modules = CourseVideoDetailModule.class, dependencies = AppComponent.class)
public interface CourseVideoDetailComponent {
    void inject(CourseVideoDetailActivity activity);
}