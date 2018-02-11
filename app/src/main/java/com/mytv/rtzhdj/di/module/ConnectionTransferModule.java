package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.ConnectionTransferContract;
import com.mytv.rtzhdj.mvp.model.ConnectionTransferModel;


@Module
public class ConnectionTransferModule {
    private ConnectionTransferContract.View view;

    /**
     * 构建ConnectionTransferModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ConnectionTransferModule(ConnectionTransferContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ConnectionTransferContract.View provideConnectionTransferView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ConnectionTransferContract.Model provideConnectionTransferModel(ConnectionTransferModel model) {
        return model;
    }
}