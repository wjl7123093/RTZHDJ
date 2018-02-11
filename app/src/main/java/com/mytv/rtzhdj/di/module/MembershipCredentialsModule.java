package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.MembershipCredentialsContract;
import com.mytv.rtzhdj.mvp.model.MembershipCredentialsModel;


@Module
public class MembershipCredentialsModule {
    private MembershipCredentialsContract.View view;

    /**
     * 构建MembershipCredentialsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MembershipCredentialsModule(MembershipCredentialsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MembershipCredentialsContract.View provideMembershipCredentialsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MembershipCredentialsContract.Model provideMembershipCredentialsModel(MembershipCredentialsModel model) {
        return model;
    }
}