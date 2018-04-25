package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.VoteOnlineModule;

import com.mytv.rtzhdj.mvp.ui.activity.VoteOnlineActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.VoteOnlineFragment;

@ActivityScope
@Component(modules = VoteOnlineModule.class, dependencies = AppComponent.class)
public interface VoteOnlineComponent {
    void inject(VoteOnlineActivity activity);
    void inject(VoteOnlineFragment fragment);
}