package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.CourseDetailModule;

import com.mytv.rtzhdj.mvp.ui.activity.CourseDetailActivity;

@ActivityScope
@Component(modules = CourseDetailModule.class, dependencies = AppComponent.class)
public interface CourseDetailComponent {
    void inject(CourseDetailActivity activity);
}