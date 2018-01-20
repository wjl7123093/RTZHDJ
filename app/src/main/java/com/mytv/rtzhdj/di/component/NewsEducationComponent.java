package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.NewsEducationModule;

import com.mytv.rtzhdj.mvp.ui.activity.NewsEducationActivity;

@ActivityScope
@Component(modules = NewsEducationModule.class, dependencies = AppComponent.class)
public interface NewsEducationComponent {
    void inject(NewsEducationActivity activity);
}