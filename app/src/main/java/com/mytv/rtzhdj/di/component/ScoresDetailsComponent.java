package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.ScoresDetailsModule;

import com.mytv.rtzhdj.mvp.ui.activity.ScoresDetailsActivity;

@ActivityScope
@Component(modules = ScoresDetailsModule.class, dependencies = AppComponent.class)
public interface ScoresDetailsComponent {
    void inject(ScoresDetailsActivity activity);
}