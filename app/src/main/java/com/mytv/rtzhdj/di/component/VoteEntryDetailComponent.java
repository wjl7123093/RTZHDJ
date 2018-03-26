package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.VoteEntryDetailModule;

import com.mytv.rtzhdj.mvp.ui.activity.VoteEntryDetailActivity;

@ActivityScope
@Component(modules = VoteEntryDetailModule.class, dependencies = AppComponent.class)
public interface VoteEntryDetailComponent {
    void inject(VoteEntryDetailActivity activity);
}