package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.UpdateInfoContract;
import com.mytv.rtzhdj.mvp.model.UpdateInfoModel;


@Module
public class UpdateInfoModule {
    private UpdateInfoContract.View view;

    /**
     * 构建UpdateInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UpdateInfoModule(UpdateInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UpdateInfoContract.View provideUpdateInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    UpdateInfoContract.Model provideUpdateInfoModel(UpdateInfoModel model) {
        return model;
    }
}