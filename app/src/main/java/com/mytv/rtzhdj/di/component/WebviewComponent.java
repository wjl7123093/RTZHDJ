package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.WebviewModule;

import com.mytv.rtzhdj.mvp.ui.activity.WebviewActivity;

@ActivityScope
@Component(modules = WebviewModule.class, dependencies = AppComponent.class)
public interface WebviewComponent {
    void inject(WebviewActivity activity);
}