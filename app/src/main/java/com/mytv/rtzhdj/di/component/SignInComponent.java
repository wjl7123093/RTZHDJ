package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.SignInModule;

import com.mytv.rtzhdj.mvp.ui.activity.SignInActivity;

@ActivityScope
@Component(modules = SignInModule.class, dependencies = AppComponent.class)
public interface SignInComponent {
    void inject(SignInActivity activity);
}