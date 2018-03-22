package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.GetVertifyCodeContract;
import com.mytv.rtzhdj.mvp.model.GetVertifyCodeModel;


@Module
public class GetVertifyCodeModule {
    private GetVertifyCodeContract.View view;

    /**
     * 构建GetVertifyCodeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GetVertifyCodeModule(GetVertifyCodeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GetVertifyCodeContract.View provideGetVertifyCodeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GetVertifyCodeContract.Model provideGetVertifyCodeModel(GetVertifyCodeModel model) {
        return model;
    }
}