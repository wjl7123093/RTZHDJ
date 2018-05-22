package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.mytv.rtzhdj.mvp.contract.OrganizationContract;
import com.mytv.rtzhdj.mvp.model.OrganizationModel;

import dagger.Module;
import dagger.Provides;


@Module
public class OrganizationModule {
    private OrganizationContract.View view;

    /**
     * 构建OrganizationModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OrganizationModule(OrganizationContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrganizationContract.View provideOrganizationView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrganizationContract.Model provideOrganizationModel(OrganizationModel model) {
        return model;
    }
}