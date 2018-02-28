package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.BindingMobileModule;

import com.mytv.rtzhdj.mvp.ui.activity.BindingMobileActivity;

@ActivityScope
@Component(modules = BindingMobileModule.class, dependencies = AppComponent.class)
public interface BindingMobileComponent {
    void inject(BindingMobileActivity activity);
}