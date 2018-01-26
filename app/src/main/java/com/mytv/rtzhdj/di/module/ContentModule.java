package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.ContentContract;
import com.mytv.rtzhdj.mvp.model.ContentModel;


@Module
public class ContentModule {
    private ContentContract.View view;

    /**
     * 构建ContentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ContentModule(ContentContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ContentContract.View provideContentView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ContentContract.Model provideContentModel(ContentModel model) {
        return model;
    }
}