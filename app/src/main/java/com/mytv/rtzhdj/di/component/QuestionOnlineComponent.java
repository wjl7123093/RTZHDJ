package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mytv.rtzhdj.di.module.QuestionOnlineModule;
import com.mytv.rtzhdj.mvp.ui.activity.QuestionOnlineActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.QuestionOnlineFragment;

import dagger.Component;

@ActivityScope
@Component(modules = QuestionOnlineModule.class, dependencies = AppComponent.class)
public interface QuestionOnlineComponent {
    void inject(QuestionOnlineActivity activity);
    void inject(QuestionOnlineFragment fragment);
}