package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.SpecialSubDetailModule;

import com.mytv.rtzhdj.mvp.ui.fragment.SpecialSubDetailFragment;

@ActivityScope
@Component(modules = SpecialSubDetailModule.class, dependencies = AppComponent.class)
public interface SpecialSubDetailComponent {
    void inject(SpecialSubDetailFragment fragment);
}