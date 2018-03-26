package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.VoteDetailContract;
import com.mytv.rtzhdj.mvp.model.VoteDetailModel;


@Module
public class VoteDetailModule {
    private VoteDetailContract.View view;

    /**
     * 构建VoteDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public VoteDetailModule(VoteDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    VoteDetailContract.View provideVoteDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    VoteDetailContract.Model provideVoteDetailModel(VoteDetailModel model) {
        return model;
    }
}