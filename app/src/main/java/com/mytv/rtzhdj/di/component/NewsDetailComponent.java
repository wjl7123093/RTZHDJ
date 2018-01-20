package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.NewsDetailModule;

import com.mytv.rtzhdj.mvp.ui.activity.NewsDetailActivity;

@ActivityScope
@Component(modules = NewsDetailModule.class, dependencies = AppComponent.class)
public interface NewsDetailComponent {
    void inject(NewsDetailActivity activity);
}