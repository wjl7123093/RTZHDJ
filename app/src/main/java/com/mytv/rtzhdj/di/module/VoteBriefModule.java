package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.VoteBriefContract;
import com.mytv.rtzhdj.mvp.model.VoteBriefModel;


@Module
public class VoteBriefModule {
    private VoteBriefContract.View view;

    /**
     * 构建VoteBriefModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public VoteBriefModule(VoteBriefContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    VoteBriefContract.View provideVoteBriefView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    VoteBriefContract.Model provideVoteBriefModel(VoteBriefModel model) {
        return model;
    }
}