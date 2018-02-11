package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.PartyMemberModule;

import com.mytv.rtzhdj.mvp.ui.activity.PartyMemberActivity;

@ActivityScope
@Component(modules = PartyMemberModule.class, dependencies = AppComponent.class)
public interface PartyMemberComponent {
    void inject(PartyMemberActivity activity);
}