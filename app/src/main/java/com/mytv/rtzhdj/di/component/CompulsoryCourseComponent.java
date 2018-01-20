package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.CompulsoryCourseModule;

import com.mytv.rtzhdj.mvp.ui.activity.CompulsoryCourseActivity;

@ActivityScope
@Component(modules = CompulsoryCourseModule.class, dependencies = AppComponent.class)
public interface CompulsoryCourseComponent {
    void inject(CompulsoryCourseActivity activity);
}