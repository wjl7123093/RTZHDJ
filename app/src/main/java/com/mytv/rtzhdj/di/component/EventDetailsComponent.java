package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.EventDetailsModule;

import com.mytv.rtzhdj.mvp.ui.activity.EventDetailsActivity;

@ActivityScope
@Component(modules = EventDetailsModule.class, dependencies = AppComponent.class)
public interface EventDetailsComponent {
    void inject(EventDetailsActivity activity);
}