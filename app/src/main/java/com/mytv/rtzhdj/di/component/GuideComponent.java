package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.GuideModule;

import com.mytv.rtzhdj.mvp.ui.activity.GuideActivity;

@ActivityScope
@Component(modules = GuideModule.class, dependencies = AppComponent.class)
public interface GuideComponent {
    void inject(GuideActivity activity);
}