package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.MyDonationModule;

import com.mytv.rtzhdj.mvp.ui.activity.MyDonationActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.MyDonationFragment;

@ActivityScope
@Component(modules = MyDonationModule.class, dependencies = AppComponent.class)
public interface MyDonationComponent {
    void inject(MyDonationActivity activity);

    void inject(MyDonationFragment fragment);
}