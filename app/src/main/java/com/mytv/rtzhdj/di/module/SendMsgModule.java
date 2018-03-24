package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.SendMsgContract;
import com.mytv.rtzhdj.mvp.model.SendMsgModel;


@Module
public class SendMsgModule {
    private SendMsgContract.View view;

    /**
     * 构建SendMsgModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SendMsgModule(SendMsgContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SendMsgContract.View provideSendMsgView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SendMsgContract.Model provideSendMsgModel(SendMsgModel model) {
        return model;
    }
}