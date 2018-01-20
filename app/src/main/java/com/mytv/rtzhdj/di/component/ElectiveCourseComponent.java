package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.ElectiveCourseModule;

import com.mytv.rtzhdj.mvp.ui.activity.ElectiveCourseActivity;

@ActivityScope
@Component(modules = ElectiveCourseModule.class, dependencies = AppComponent.class)
public interface ElectiveCourseComponent {
    void inject(ElectiveCourseActivity activity);
}