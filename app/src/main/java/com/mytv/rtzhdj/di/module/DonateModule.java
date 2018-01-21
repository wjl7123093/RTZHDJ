package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.DonateContract;
import com.mytv.rtzhdj.mvp.model.DonateModel;


@Module
public class DonateModule {
    private DonateContract.View view;

    /**
     * 构建DonateModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public DonateModule(DonateContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DonateContract.View provideDonateView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    DonateContract.Model provideDonateModel(DonateModel model) {
        return model;
    }
}