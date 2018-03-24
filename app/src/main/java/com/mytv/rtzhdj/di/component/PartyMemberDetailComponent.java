package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.PartyMemberDetailModule;

import com.mytv.rtzhdj.mvp.ui.activity.PartyMemberDetailActivity;

@ActivityScope
@Component(modules = PartyMemberDetailModule.class, dependencies = AppComponent.class)
public interface PartyMemberDetailComponent {
    void inject(PartyMemberDetailActivity activity);
}