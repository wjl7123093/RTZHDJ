package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.ContentModule;

import com.mytv.rtzhdj.mvp.ui.fragment.ContentFragment;

@ActivityScope
@Component(modules = ContentModule.class, dependencies = AppComponent.class)
public interface ContentComponent {
    void inject(ContentFragment fragment);
}