package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.NewsLiveModule;

import com.mytv.rtzhdj.mvp.ui.activity.NewsLiveActivity;

@ActivityScope
@Component(modules = NewsLiveModule.class, dependencies = AppComponent.class)
public interface NewsLiveComponent {
    void inject(NewsLiveActivity activity);
}