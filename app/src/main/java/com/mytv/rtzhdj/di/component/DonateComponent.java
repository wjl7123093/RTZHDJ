package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.DonateModule;

import com.mytv.rtzhdj.mvp.ui.activity.DonateActivity;

@ActivityScope
@Component(modules = DonateModule.class, dependencies = AppComponent.class)
public interface DonateComponent {
    void inject(DonateActivity activity);
}