package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.ReportCommunityModule;

import com.mytv.rtzhdj.mvp.ui.activity.ReportCommunityActivity;

@ActivityScope
@Component(modules = ReportCommunityModule.class, dependencies = AppComponent.class)
public interface ReportCommunityComponent {
    void inject(ReportCommunityActivity activity);
}