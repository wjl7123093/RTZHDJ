package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.WishWallContract;
import com.mytv.rtzhdj.mvp.model.WishWallModel;


@Module
public class WishWallModule {
    private WishWallContract.View view;

    /**
     * 构建WishWallModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public WishWallModule(WishWallContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WishWallContract.View provideWishWallView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WishWallContract.Model provideWishWallModel(WishWallModel model) {
        return model;
    }
}