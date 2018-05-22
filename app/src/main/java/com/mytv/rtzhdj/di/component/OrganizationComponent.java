package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mytv.rtzhdj.di.module.OrganizationModule;
import com.mytv.rtzhdj.mvp.ui.activity.OrganizationActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.OrganizationFragment;

import dagger.Component;

@ActivityScope
@Component(modules = OrganizationModule.class, dependencies = AppComponent.class)
public interface OrganizationComponent {
    void inject(OrganizationActivity activity);

    void inject(OrganizationFragment fragment);
}