package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.SettingsModule;

import com.mytv.rtzhdj.mvp.ui.activity.SettingsActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.SettingsFragment;

@ActivityScope
@Component(modules = SettingsModule.class, dependencies = AppComponent.class)
public interface SettingsComponent {
    void inject(SettingsActivity activity);

    void inject(SettingsFragment fragment);
}