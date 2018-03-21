package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.FindPwdContract;
import com.mytv.rtzhdj.mvp.model.FindPwdModel;


@Module
public class FindPwdModule {
    private FindPwdContract.View view;

    /**
     * 构建FindPwdModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public FindPwdModule(FindPwdContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FindPwdContract.View provideFindPwdView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FindPwdContract.Model provideFindPwdModel(FindPwdModel model) {
        return model;
    }
}