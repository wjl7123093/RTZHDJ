package com.mytv.rtzhdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mytv.rtzhdj.mvp.contract.CommentContract;
import com.mytv.rtzhdj.mvp.model.CommentModel;


@Module
public class CommentModule {
    private CommentContract.View view;

    /**
     * 构建CommentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CommentModule(CommentContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommentContract.View provideCommentView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommentContract.Model provideCommentModel(CommentModel model) {
        return model;
    }
}