package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.MyMsgModule;

import com.mytv.rtzhdj.mvp.ui.activity.MyMsgActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.MyMsgFragment;

@ActivityScope
@Component(modules = MyMsgModule.class, dependencies = AppComponent.class)
public interface MyMsgComponent {
    void inject(MyMsgActivity activity);

    void inject(MyMsgFragment fragment);
}