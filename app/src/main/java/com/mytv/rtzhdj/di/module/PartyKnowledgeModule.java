package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.PartyKnowledgeContract;
import com.mytv.rtzhdj.mvp.model.PartyKnowledgeModel;


@Module
public class PartyKnowledgeModule {
    private PartyKnowledgeContract.View view;

    /**
     * 构建PartyKnowledgeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PartyKnowledgeModule(PartyKnowledgeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PartyKnowledgeContract.View providePartyKnowledgeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PartyKnowledgeContract.Model providePartyKnowledgeModel(PartyKnowledgeModel model) {
        return model;
    }
}