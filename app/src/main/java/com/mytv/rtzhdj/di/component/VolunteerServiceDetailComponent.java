package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.VolunteerServiceDetailModule;

import com.mytv.rtzhdj.mvp.ui.activity.VolunteerServiceDetailActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.VolunteerServiceDetailFragment;

@ActivityScope
@Component(modules = VolunteerServiceDetailModule.class, dependencies = AppComponent.class)
public interface VolunteerServiceDetailComponent {
    void inject(VolunteerServiceDetailActivity activity);

    void inject(VolunteerServiceDetailFragment fragment);
}