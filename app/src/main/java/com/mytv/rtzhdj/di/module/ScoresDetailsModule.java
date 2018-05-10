package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.ScoresDetailsContract;
import com.mytv.rtzhdj.mvp.model.ScoresDetailsModel;


@Module
public class ScoresDetailsModule {
    private ScoresDetailsContract.View view;

    /**
     * 构建ScoresDetailsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ScoresDetailsModule(ScoresDetailsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ScoresDetailsContract.View provideScoresDetailsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ScoresDetailsContract.Model provideScoresDetailsModel(ScoresDetailsModel model) {
        return model;
    }
}