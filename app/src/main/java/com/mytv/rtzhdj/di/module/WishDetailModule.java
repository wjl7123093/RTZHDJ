package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.WishDetailContract;
import com.mytv.rtzhdj.mvp.model.WishDetailModel;


@Module
public class WishDetailModule {
    private WishDetailContract.View view;

    /**
     * 构建WishDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public WishDetailModule(WishDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WishDetailContract.View provideWishDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WishDetailContract.Model provideWishDetailModel(WishDetailModel model) {
        return model;
    }
}