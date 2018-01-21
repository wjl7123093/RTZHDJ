package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.SignInContract;
import com.mytv.rtzhdj.mvp.model.SignInModel;


@Module
public class SignInModule {
    private SignInContract.View view;

    /**
     * 构建SignInModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SignInModule(SignInContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SignInContract.View provideSignInView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SignInContract.Model provideSignInModel(SignInModel model) {
        return model;
    }
}