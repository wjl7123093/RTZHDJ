package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.SpecialSubDetailContract;
import com.mytv.rtzhdj.mvp.model.SpecialSubDetailModel;


@Module
public class SpecialSubDetailModule {
    private SpecialSubDetailContract.View view;

    /**
     * 构建SpecialSubDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SpecialSubDetailModule(SpecialSubDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SpecialSubDetailContract.View provideSpecialSubDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SpecialSubDetailContract.Model provideSpecialSubDetailModel(SpecialSubDetailModel model) {
        return model;
    }
}