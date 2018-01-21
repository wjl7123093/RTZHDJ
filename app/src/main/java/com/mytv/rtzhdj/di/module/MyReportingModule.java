package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.MyReportingContract;
import com.mytv.rtzhdj.mvp.model.MyReportingModel;


@Module
public class MyReportingModule {
    private MyReportingContract.View view;

    /**
     * 构建MyReportingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyReportingModule(MyReportingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyReportingContract.View provideMyReportingView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyReportingContract.Model provideMyReportingModel(MyReportingModel model) {
        return model;
    }
}