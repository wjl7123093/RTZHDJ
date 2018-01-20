package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.EffectEvaluationContract;
import com.mytv.rtzhdj.mvp.model.EffectEvaluationModel;


@Module
public class EffectEvaluationModule {
    private EffectEvaluationContract.View view;

    /**
     * 构建EffectEvaluationModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EffectEvaluationModule(EffectEvaluationContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EffectEvaluationContract.View provideEffectEvaluationView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EffectEvaluationContract.Model provideEffectEvaluationModel(EffectEvaluationModel model) {
        return model;
    }
}