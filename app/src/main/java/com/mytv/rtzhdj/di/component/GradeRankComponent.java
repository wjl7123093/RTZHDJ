package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.GradeRankModule;

import com.mytv.rtzhdj.mvp.ui.activity.GradeRankActivity;

@ActivityScope
@Component(modules = GradeRankModule.class, dependencies = AppComponent.class)
public interface GradeRankComponent {
    void inject(GradeRankActivity activity);
}