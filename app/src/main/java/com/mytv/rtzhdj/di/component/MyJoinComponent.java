package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.MyJoinModule;

import com.mytv.rtzhdj.mvp.ui.activity.MyJoinActivity;

@ActivityScope
@Component(modules = MyJoinModule.class, dependencies = AppComponent.class)
public interface MyJoinComponent {
    void inject(MyJoinActivity activity);
}