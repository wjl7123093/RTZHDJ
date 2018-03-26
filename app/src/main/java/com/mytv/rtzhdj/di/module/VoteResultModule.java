package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.VoteResultContract;
import com.mytv.rtzhdj.mvp.model.VoteResultModel;


@Module
public class VoteResultModule {
    private VoteResultContract.View view;

    /**
     * 构建VoteResultModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public VoteResultModule(VoteResultContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    VoteResultContract.View provideVoteResultView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    VoteResultContract.Model provideVoteResultModel(VoteResultModel model) {
        return model;
    }
}