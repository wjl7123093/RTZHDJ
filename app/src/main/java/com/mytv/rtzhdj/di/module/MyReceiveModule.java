package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.MyReceiveContract;
import com.mytv.rtzhdj.mvp.model.MyReceiveModel;


@Module
public class MyReceiveModule {
    private MyReceiveContract.View view;

    /**
     * 构建MyReceiveModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyReceiveModule(MyReceiveContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyReceiveContract.View provideMyReceiveView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyReceiveContract.Model provideMyReceiveModel(MyReceiveModel model) {
        return model;
    }
}