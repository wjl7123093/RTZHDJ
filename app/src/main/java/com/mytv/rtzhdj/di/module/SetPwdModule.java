package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.SetPwdContract;
import com.mytv.rtzhdj.mvp.model.SetPwdModel;


@Module
public class SetPwdModule {
    private SetPwdContract.View view;

    /**
     * 构建SetPwdModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SetPwdModule(SetPwdContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SetPwdContract.View provideSetPwdView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SetPwdContract.Model provideSetPwdModel(SetPwdModel model) {
        return model;
    }
}