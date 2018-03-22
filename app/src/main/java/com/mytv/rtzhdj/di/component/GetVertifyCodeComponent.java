package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.GetVertifyCodeModule;

import com.mytv.rtzhdj.mvp.ui.activity.GetVertifyCodeActivity;

@ActivityScope
@Component(modules = GetVertifyCodeModule.class, dependencies = AppComponent.class)
public interface GetVertifyCodeComponent {
    void inject(GetVertifyCodeActivity activity);
}