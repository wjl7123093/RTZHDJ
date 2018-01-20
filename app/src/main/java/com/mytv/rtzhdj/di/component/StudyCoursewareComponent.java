package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.StudyCoursewareModule;

import com.mytv.rtzhdj.mvp.ui.activity.StudyCoursewareActivity;

@ActivityScope
@Component(modules = StudyCoursewareModule.class, dependencies = AppComponent.class)
public interface StudyCoursewareComponent {
    void inject(StudyCoursewareActivity activity);
}