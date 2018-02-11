package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.PartyMemberContract;
import com.mytv.rtzhdj.mvp.model.PartyMemberModel;


@Module
public class PartyMemberModule {
    private PartyMemberContract.View view;

    /**
     * 构建PartyMemberModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PartyMemberModule(PartyMemberContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PartyMemberContract.View providePartyMemberView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PartyMemberContract.Model providePartyMemberModel(PartyMemberModel model) {
        return model;
    }
}