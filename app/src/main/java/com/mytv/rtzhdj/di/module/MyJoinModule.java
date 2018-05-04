package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.MyJoinContract;
import com.mytv.rtzhdj.mvp.model.MyJoinModel;


@Module
public class MyJoinModule {
    private MyJoinContract.View view;

    /**
     * 构建MyJoinModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyJoinModule(MyJoinContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyJoinContract.View provideMyJoinView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyJoinContract.Model provideMyJoinModel(MyJoinModel model) {
        return model;
    }
}