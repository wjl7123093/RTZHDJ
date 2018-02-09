package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.MyReceiveWishContract;
import com.mytv.rtzhdj.mvp.model.MyReceiveWishModel;


@Module
public class MyReceiveWishModule {
    private MyReceiveWishContract.View view;

    /**
     * 构建MyReceiveWishModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyReceiveWishModule(MyReceiveWishContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyReceiveWishContract.View provideMyReceiveWishView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyReceiveWishContract.Model provideMyReceiveWishModel(MyReceiveWishModel model) {
        return model;
    }
}