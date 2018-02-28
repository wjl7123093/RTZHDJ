package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.UpdatePwdModule;

import com.mytv.rtzhdj.mvp.ui.activity.UpdatePwdActivity;

@ActivityScope
@Component(modules = UpdatePwdModule.class, dependencies = AppComponent.class)
public interface UpdatePwdComponent {
    void inject(UpdatePwdActivity activity);
}