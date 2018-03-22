package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.SetPwdModule;

import com.mytv.rtzhdj.mvp.ui.activity.SetPwdActivity;

@ActivityScope
@Component(modules = SetPwdModule.class, dependencies = AppComponent.class)
public interface SetPwdComponent {
    void inject(SetPwdActivity activity);
}