package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.ReportCommunityContract;
import com.mytv.rtzhdj.mvp.model.ReportCommunityModel;


@Module
public class ReportCommunityModule {
    private ReportCommunityContract.View view;

    /**
     * 构建ReportCommunityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ReportCommunityModule(ReportCommunityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ReportCommunityContract.View provideReportCommunityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ReportCommunityContract.Model provideReportCommunityModel(ReportCommunityModel model) {
        return model;
    }
}