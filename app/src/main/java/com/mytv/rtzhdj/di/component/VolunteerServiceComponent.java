package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.VolunteerServiceModule;

import com.mytv.rtzhdj.mvp.ui.activity.VolunteerServiceActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.VolunteerServiceFragment;

@ActivityScope
@Component(modules = VolunteerServiceModule.class, dependencies = AppComponent.class)
public interface VolunteerServiceComponent {
    void inject(VolunteerServiceActivity activity);
    void inject(VolunteerServiceFragment fragment);
}