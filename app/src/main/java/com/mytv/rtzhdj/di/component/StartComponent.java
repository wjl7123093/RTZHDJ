package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.StartModule;

import com.mytv.rtzhdj.mvp.ui.activity.StartActivity;

@ActivityScope
@Component(modules = StartModule.class, dependencies = AppComponent.class)
public interface StartComponent {
    void inject(StartActivity activity);
}