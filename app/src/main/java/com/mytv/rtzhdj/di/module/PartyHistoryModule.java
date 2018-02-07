package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.PartyHistoryContract;
import com.mytv.rtzhdj.mvp.model.PartyHistoryModel;


@Module
public class PartyHistoryModule {
    private PartyHistoryContract.View view;

    /**
     * 构建PartyHistoryModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PartyHistoryModule(PartyHistoryContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PartyHistoryContract.View providePartyHistoryView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PartyHistoryContract.Model providePartyHistoryModel(PartyHistoryModel model) {
        return model;
    }
}