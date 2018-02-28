package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.GradeRankContract;
import com.mytv.rtzhdj.mvp.model.GradeRankModel;


@Module
public class GradeRankModule {
    private GradeRankContract.View view;

    /**
     * 构建GradeRankModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GradeRankModule(GradeRankContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GradeRankContract.View provideGradeRankView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GradeRankContract.Model provideGradeRankModel(GradeRankModel model) {
        return model;
    }
}