package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.VoteOnlineContract;
import com.mytv.rtzhdj.mvp.model.VoteOnlineModel;


@Module
public class VoteOnlineModule {
    private VoteOnlineContract.View view;

    /**
     * 构建VoteOnlineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public VoteOnlineModule(VoteOnlineContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    VoteOnlineContract.View provideVoteOnlineView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    VoteOnlineContract.Model provideVoteOnlineModel(VoteOnlineModel model) {
        return model;
    }
}