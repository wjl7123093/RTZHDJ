package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.BindingMobileContract;
import com.mytv.rtzhdj.mvp.model.BindingMobileModel;


@Module
public class BindingMobileModule {
    private BindingMobileContract.View view;

    /**
     * 构建BindingMobileModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public BindingMobileModule(BindingMobileContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BindingMobileContract.View provideBindingMobileView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    BindingMobileContract.Model provideBindingMobileModel(BindingMobileModel model) {
        return model;
    }
}