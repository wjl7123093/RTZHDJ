package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.StudyRecordModule;

import com.mytv.rtzhdj.mvp.ui.activity.StudyRecordActivity;

@ActivityScope
@Component(modules = StudyRecordModule.class, dependencies = AppComponent.class)
public interface StudyRecordComponent {
    void inject(StudyRecordActivity activity);
}