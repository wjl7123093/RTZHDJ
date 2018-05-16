package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.QuestionnaireModule;

import com.mytv.rtzhdj.mvp.ui.activity.QuestionnaireActivity;

@ActivityScope
@Component(modules = QuestionnaireModule.class, dependencies = AppComponent.class)
public interface QuestionnaireComponent {
    void inject(QuestionnaireActivity activity);
}