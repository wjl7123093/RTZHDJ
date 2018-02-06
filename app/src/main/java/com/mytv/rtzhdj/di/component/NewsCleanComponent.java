package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.NewsCleanModule;

import com.mytv.rtzhdj.mvp.ui.activity.NewsCleanActivity;

@ActivityScope
@Component(modules = NewsCleanModule.class, dependencies = AppComponent.class)
public interface NewsCleanComponent {
    void inject(NewsCleanActivity activity);
}