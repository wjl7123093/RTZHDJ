package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.JoinModule;

import com.mytv.rtzhdj.mvp.ui.fragment.JoinFragment;

@ActivityScope
@Component(modules = JoinModule.class, dependencies = AppComponent.class)
public interface JoinComponent {
    void inject(JoinFragment fragment);
}