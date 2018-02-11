package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.ConnectionTransferModule;

import com.mytv.rtzhdj.mvp.ui.activity.ConnectionTransferActivity;

@ActivityScope
@Component(modules = ConnectionTransferModule.class, dependencies = AppComponent.class)
public interface ConnectionTransferComponent {
    void inject(ConnectionTransferActivity activity);
}