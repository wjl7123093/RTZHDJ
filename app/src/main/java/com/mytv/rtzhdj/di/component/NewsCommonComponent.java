package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.NewsCommonModule;

import com.mytv.rtzhdj.mvp.ui.activity.NewsCommonActivity;

@ActivityScope
@Component(modules = NewsCommonModule.class, dependencies = AppComponent.class)
public interface NewsCommonComponent {
    void inject(NewsCommonActivity activity);
}