package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.ElectiveCourseContract;
import com.mytv.rtzhdj.mvp.model.ElectiveCourseModel;


@Module
public class ElectiveCourseModule {
    private ElectiveCourseContract.View view;

    /**
     * 构建ElectiveCourseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ElectiveCourseModule(ElectiveCourseContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ElectiveCourseContract.View provideElectiveCourseView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ElectiveCourseContract.Model provideElectiveCourseModel(ElectiveCourseModel model) {
        return model;
    }
}