package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.VoteEntryDetailContract;
import com.mytv.rtzhdj.mvp.model.VoteEntryDetailModel;


@Module
public class VoteEntryDetailModule {
    private VoteEntryDetailContract.View view;

    /**
     * 构建VoteEntryDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public VoteEntryDetailModule(VoteEntryDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    VoteEntryDetailContract.View provideVoteEntryDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    VoteEntryDetailContract.Model provideVoteEntryDetailModel(VoteEntryDetailModel model) {
        return model;
    }
}