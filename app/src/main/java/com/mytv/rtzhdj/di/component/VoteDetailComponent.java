package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.VoteDetailModule;

import com.mytv.rtzhdj.mvp.ui.activity.VoteDetailActivity;

@ActivityScope
@Component(modules = VoteDetailModule.class, dependencies = AppComponent.class)
public interface VoteDetailComponent {
    void inject(VoteDetailActivity activity);
}