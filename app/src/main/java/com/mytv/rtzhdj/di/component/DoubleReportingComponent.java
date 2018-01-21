package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.DoubleReportingModule;

import com.mytv.rtzhdj.mvp.ui.activity.DoubleReportingActivity;

@ActivityScope
@Component(modules = DoubleReportingModule.class, dependencies = AppComponent.class)
public interface DoubleReportingComponent {
    void inject(DoubleReportingActivity activity);
}