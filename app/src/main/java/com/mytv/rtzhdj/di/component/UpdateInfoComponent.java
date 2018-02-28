package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.UpdateInfoModule;

import com.mytv.rtzhdj.mvp.ui.activity.UpdateInfoActivity;

@ActivityScope
@Component(modules = UpdateInfoModule.class, dependencies = AppComponent.class)
public interface UpdateInfoComponent {
    void inject(UpdateInfoActivity activity);
}