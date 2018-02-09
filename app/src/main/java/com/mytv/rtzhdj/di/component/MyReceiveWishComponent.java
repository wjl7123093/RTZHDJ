package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.MyReceiveWishModule;

import com.mytv.rtzhdj.mvp.ui.activity.MyReceiveWishActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.MyReceiveWishFragment;

@ActivityScope
@Component(modules = MyReceiveWishModule.class, dependencies = AppComponent.class)
public interface MyReceiveWishComponent {
    void inject(MyReceiveWishActivity activity);

    void inject(MyReceiveWishFragment fragment);
}