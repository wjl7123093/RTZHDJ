package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.mytv.rtzhdj.mvp.contract.QuestionaireSurveyContract;
import com.mytv.rtzhdj.mvp.model.QuestionaireSurveyModel;

import dagger.Module;
import dagger.Provides;


@Module
public class QuestionaireSurveyModule {
    private QuestionaireSurveyContract.View view;

    /**
     * 构建QuestionaireSurveyModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public QuestionaireSurveyModule(QuestionaireSurveyContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    QuestionaireSurveyContract.View provideQuestionaireSurveyView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    QuestionaireSurveyContract.Model provideQuestionaireSurveyModel(QuestionaireSurveyModel model) {
        return model;
    }
}