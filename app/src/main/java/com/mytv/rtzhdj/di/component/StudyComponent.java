package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.StudyModule;

import com.mytv.rtzhdj.mvp.ui.fragment.StudyFragment;

@ActivityScope
@Component(modules = StudyModule.class, dependencies = AppComponent.class)
public interface StudyComponent {
    void inject(StudyFragment fragment);
}