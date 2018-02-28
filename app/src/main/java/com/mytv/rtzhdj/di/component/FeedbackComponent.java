package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.FeedbackModule;

import com.mytv.rtzhdj.mvp.ui.activity.FeedbackActivity;

@ActivityScope
@Component(modules = FeedbackModule.class, dependencies = AppComponent.class)
public interface FeedbackComponent {
    void inject(FeedbackActivity activity);
}