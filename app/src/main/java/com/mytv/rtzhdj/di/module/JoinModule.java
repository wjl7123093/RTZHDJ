package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.JoinContract;
import com.mytv.rtzhdj.mvp.model.JoinModel;


@Module
public class JoinModule {
    private JoinContract.View view;

    /**
     * 构建JoinModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public JoinModule(JoinContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    JoinContract.View provideJoinView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    JoinContract.Model provideJoinModel(JoinModel model) {
        return model;
    }
}