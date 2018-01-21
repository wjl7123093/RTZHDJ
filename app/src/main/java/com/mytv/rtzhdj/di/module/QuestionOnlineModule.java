package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.QuestionOnlineContract;
import com.mytv.rtzhdj.mvp.model.QuestionOnlineModel;


@Module
public class QuestionOnlineModule {
    private QuestionOnlineContract.View view;

    /**
     * 构建QuestionOnlineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public QuestionOnlineModule(QuestionOnlineContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    QuestionOnlineContract.View provideQuestionOnlineView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    QuestionOnlineContract.Model provideQuestionOnlineModel(QuestionOnlineModel model) {
        return model;
    }
}