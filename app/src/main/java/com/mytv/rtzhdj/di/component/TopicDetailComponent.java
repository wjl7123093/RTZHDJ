package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.TopicDetailModule;

import com.mytv.rtzhdj.mvp.ui.activity.TopicDetailActivity;

@ActivityScope
@Component(modules = TopicDetailModule.class, dependencies = AppComponent.class)
public interface TopicDetailComponent {
    void inject(TopicDetailActivity activity);
}