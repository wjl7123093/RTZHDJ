package com.mytv.rtzhdj.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mytv.rtzhdj.di.module.PartyKnowledgeModule;

import com.mytv.rtzhdj.mvp.ui.activity.PartyKnowledgeActivity;
import com.mytv.rtzhdj.mvp.ui.fragment.PartyKnowledgeFragment;

@ActivityScope
@Component(modules = PartyKnowledgeModule.class, dependencies = AppComponent.class)
public interface PartyKnowledgeComponent {
    void inject(PartyKnowledgeActivity activity);
    void inject(PartyKnowledgeFragment fragment);
}