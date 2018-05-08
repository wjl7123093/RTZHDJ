package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.NewsAllModule;

import com.mytv.rtzhdj.mvp.ui.activity.NewsAllActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.NewsAllFragment;

@ActivityScope
@Component(modules = NewsAllModule.class, dependencies = AppComponent.class)
public interface NewsAllComponent {
    void inject(NewsAllActivity activity);

    void inject(NewsAllFragment fragment);
}