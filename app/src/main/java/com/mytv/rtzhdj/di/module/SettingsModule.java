package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.SettingsContract;
import com.mytv.rtzhdj.mvp.model.SettingsModel;


@Module
public class SettingsModule {
    private SettingsContract.View view;

    /**
     * 构建SettingsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SettingsModule(SettingsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SettingsContract.View provideSettingsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SettingsContract.Model provideSettingsModel(SettingsModel model) {
        return model;
    }
}