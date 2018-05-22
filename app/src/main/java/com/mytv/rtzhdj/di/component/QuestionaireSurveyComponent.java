package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mytv.rtzhdj.di.module.QuestionaireSurveyModule;
import com.mytv.rtzhdj.mvp.ui.activity.QuestionaireSurveyActivity;

import dagger.Component;

@ActivityScope
@Component(modules = QuestionaireSurveyModule.class, dependencies = AppComponent.class)
public interface QuestionaireSurveyComponent {
    void inject(QuestionaireSurveyActivity activity);
}