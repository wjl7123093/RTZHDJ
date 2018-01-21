package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.OrganizationalContract;
import com.mytv.rtzhdj.mvp.model.OrganizationalModel;


@Module
public class OrganizationalModule {
    private OrganizationalContract.View view;

    /**
     * 构建OrganizationalModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OrganizationalModule(OrganizationalContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrganizationalContract.View provideOrganizationalView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrganizationalContract.Model provideOrganizationalModel(OrganizationalModel model) {
        return model;
    }
}