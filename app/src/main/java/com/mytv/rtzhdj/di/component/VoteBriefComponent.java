package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.VoteBriefModule;

import com.mytv.rtzhdj.mvp.ui.activity.VoteBriefActivity;

@ActivityScope
@Component(modules = VoteBriefModule.class, dependencies = AppComponent.class)
public interface VoteBriefComponent {
    void inject(VoteBriefActivity activity);
}