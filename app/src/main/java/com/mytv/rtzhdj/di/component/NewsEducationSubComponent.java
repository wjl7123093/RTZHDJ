package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mytv.rtzhdj.di.module.NewsEducationSubModule;
import com.mytv.rtzhdj.mvp.ui.activity.NewsEducationSubActivity;

import dagger.Component;

@ActivityScope
@Component(modules = NewsEducationSubModule.class, dependencies = AppComponent.class)
public interface NewsEducationSubComponent {
    void inject(NewsEducationSubActivity activity);
}