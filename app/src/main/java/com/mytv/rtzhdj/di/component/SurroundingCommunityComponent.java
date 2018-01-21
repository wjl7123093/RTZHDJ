package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.SurroundingCommunityModule;

import com.mytv.rtzhdj.mvp.ui.activity.SurroundingCommunityActivity;

@ActivityScope
@Component(modules = SurroundingCommunityModule.class, dependencies = AppComponent.class)
public interface SurroundingCommunityComponent {
    void inject(SurroundingCommunityActivity activity);
}