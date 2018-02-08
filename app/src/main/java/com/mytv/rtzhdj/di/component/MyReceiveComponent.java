package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.MyReceiveModule;

import com.mytv.rtzhdj.mvp.ui.activity.MyReceiveActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.MyReceiveFragment;

@ActivityScope
@Component(modules = MyReceiveModule.class, dependencies = AppComponent.class)
public interface MyReceiveComponent {
    void inject(MyReceiveActivity activity);

    void inject(MyReceiveFragment fragment);
}