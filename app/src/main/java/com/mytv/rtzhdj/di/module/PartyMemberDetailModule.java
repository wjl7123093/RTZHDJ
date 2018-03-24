package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.PartyMemberDetailContract;
import com.mytv.rtzhdj.mvp.model.PartyMemberDetailModel;


@Module
public class PartyMemberDetailModule {
    private PartyMemberDetailContract.View view;

    /**
     * 构建PartyMemberDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PartyMemberDetailModule(PartyMemberDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PartyMemberDetailContract.View providePartyMemberDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PartyMemberDetailContract.Model providePartyMemberDetailModel(PartyMemberDetailModel model) {
        return model;
    }
}