package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mytv.rtzhdj.di.module.TeacherListModule;
import com.mytv.rtzhdj.mvp.ui.activity.TeacherListActivity;

import dagger.Component;

@ActivityScope
@Component(modules = TeacherListModule.class, dependencies = AppComponent.class)
public interface TeacherListComponent {
    void inject(TeacherListActivity activity);
}