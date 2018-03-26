package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.VoteResultModule;

import com.mytv.rtzhdj.mvp.ui.activity.VoteResultActivity;

@ActivityScope
@Component(modules = VoteResultModule.class, dependencies = AppComponent.class)
public interface VoteResultComponent {
    void inject(VoteResultActivity activity);
}