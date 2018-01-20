package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.TopicDetailContract;
import com.mytv.rtzhdj.mvp.model.TopicDetailModel;


@Module
public class TopicDetailModule {
    private TopicDetailContract.View view;

    /**
     * 构建TopicDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TopicDetailModule(TopicDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TopicDetailContract.View provideTopicDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TopicDetailContract.Model provideTopicDetailModel(TopicDetailModel model) {
        return model;
    }
}