package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.NewsPovertyContract;
import com.mytv.rtzhdj.mvp.model.NewsPovertyModel;


@Module
public class NewsPovertyModule {
    private NewsPovertyContract.View view;

    /**
     * 构建NewsPovertyModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NewsPovertyModule(NewsPovertyContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewsPovertyContract.View provideNewsPovertyView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NewsPovertyContract.Model provideNewsPovertyModel(NewsPovertyModel model) {
        return model;
    }
}