package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.NewsPovertyModule;

import com.mytv.rtzhdj.mvp.ui.activity.NewsPovertyActivity;

@ActivityScope
@Component(modules = NewsPovertyModule.class, dependencies = AppComponent.class)
public interface NewsPovertyComponent {
    void inject(NewsPovertyActivity activity);
}