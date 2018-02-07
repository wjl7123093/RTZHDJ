package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.PartyHistoryModule;

import com.mytv.rtzhdj.mvp.ui.activity.PartyHistoryActivity;

@ActivityScope
@Component(modules = PartyHistoryModule.class, dependencies = AppComponent.class)
public interface PartyHistoryComponent {
    void inject(PartyHistoryActivity activity);
}