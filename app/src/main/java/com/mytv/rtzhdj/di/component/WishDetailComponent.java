package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.WishDetailModule;

import com.mytv.rtzhdj.mvp.ui.activity.WishDetailActivity;

@ActivityScope
@Component(modules = WishDetailModule.class, dependencies = AppComponent.class)
public interface WishDetailComponent {
    void inject(WishDetailActivity activity);
}