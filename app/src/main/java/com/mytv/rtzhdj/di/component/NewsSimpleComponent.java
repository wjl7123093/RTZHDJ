package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.NewsSimpleModule;

import com.mytv.rtzhdj.mvp.ui.activity.NewsSimpleActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.NewsSimpleFragment;

@ActivityScope
@Component(modules = NewsSimpleModule.class, dependencies = AppComponent.class)
public interface NewsSimpleComponent {
    void inject(NewsSimpleActivity activity);
    void inject(NewsSimpleFragment fragment);
}