package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.QuestionOnlineModule;

import com.mytv.rtzhdj.mvp.ui.activity.QuestionOnlineActivity;

@ActivityScope
@Component(modules = QuestionOnlineModule.class, dependencies = AppComponent.class)
public interface QuestionOnlineComponent {
    void inject(QuestionOnlineActivity activity);
}