package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.WishWallModule;

import com.mytv.rtzhdj.mvp.ui.activity.WishWallActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.WishWallFragment;

@ActivityScope
@Component(modules = WishWallModule.class, dependencies = AppComponent.class)
public interface WishWallComponent {
    void inject(WishWallActivity activity);
    void inject(WishWallFragment fragment);
}