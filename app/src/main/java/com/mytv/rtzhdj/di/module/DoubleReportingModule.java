package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.DoubleReportingContract;
import com.mytv.rtzhdj.mvp.model.DoubleReportingModel;


@Module
public class DoubleReportingModule {
    private DoubleReportingContract.View view;

    /**
     * 构建DoubleReportingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public DoubleReportingModule(DoubleReportingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DoubleReportingContract.View provideDoubleReportingView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    DoubleReportingContract.Model provideDoubleReportingModel(DoubleReportingModel model) {
        return model;
    }
}