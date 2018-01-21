package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.MyTaskModule;

import com.mytv.rtzhdj.mvp.ui.activity.MyTaskActivity;

@ActivityScope
@Component(modules = MyTaskModule.class, dependencies = AppComponent.class)
public interface MyTaskComponent {
    void inject(MyTaskActivity activity);
}