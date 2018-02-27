package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.EffectEvaluationModule;

import com.mytv.rtzhdj.mvp.ui.activity.EffectEvaluationActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.EffectEvaluationFragment;

@ActivityScope
@Component(modules = EffectEvaluationModule.class, dependencies = AppComponent.class)
public interface EffectEvaluationComponent {
    void inject(EffectEvaluationActivity activity);
    void inject(EffectEvaluationFragment fragment);
}