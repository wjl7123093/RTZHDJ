package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.OrganizationalModule;

import com.mytv.rtzhdj.mvp.ui.activity.OrganizationalActivity;

@ActivityScope
@Component(modules = OrganizationalModule.class, dependencies = AppComponent.class)
public interface OrganizationalComponent {
    void inject(OrganizationalActivity activity);
}