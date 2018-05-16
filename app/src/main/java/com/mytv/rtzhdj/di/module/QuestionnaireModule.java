package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.QuestionnaireContract;
import com.mytv.rtzhdj.mvp.model.QuestionnaireModel;


@Module
public class QuestionnaireModule {
    private QuestionnaireContract.View view;

    /**
     * 构建QuestionnaireModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public QuestionnaireModule(QuestionnaireContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    QuestionnaireContract.View provideQuestionnaireView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    QuestionnaireContract.Model provideQuestionnaireModel(QuestionnaireModel model) {
        return model;
    }
}