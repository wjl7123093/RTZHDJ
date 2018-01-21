package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.MyReportingModule;

import com.mytv.rtzhdj.mvp.ui.activity.MyReportingActivity;

@ActivityScope
@Component(modules = MyReportingModule.class, dependencies = AppComponent.class)
public interface MyReportingComponent {
    void inject(MyReportingActivity activity);
}