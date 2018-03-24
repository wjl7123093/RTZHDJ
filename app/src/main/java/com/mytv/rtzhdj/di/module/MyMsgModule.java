package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.MyMsgContract;
import com.mytv.rtzhdj.mvp.model.MyMsgModel;


@Module
public class MyMsgModule {
    private MyMsgContract.View view;

    /**
     * 构建MyMsgModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyMsgModule(MyMsgContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyMsgContract.View provideMyMsgView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyMsgContract.Model provideMyMsgModel(MyMsgModel model) {
        return model;
    }
}